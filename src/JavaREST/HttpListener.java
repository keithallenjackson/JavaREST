package JavaREST;

import JavaREST.Framework.*;
import JavaREST.Wrappers.ServerSocketWrap;
import JavaREST.Wrappers.ThreadPoolWrapper;
import com.sun.istack.internal.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class HttpListener extends Listener {
    private Router router;
    private Logger logger;
    private HttpStatusService statusService;
    private static int threadId = 0;

    public HttpListener(@NotNull ServerSocketWrapper wrapper,
                        @NotNull IExecutorService executor,
                        @NotNull Router router,
                        @NotNull Logger logger,
                        @NotNull HttpStatusService statusService) {

        super(wrapper, executor, logger);
        this.router = router;
        this.logger = logger;
        this.statusService = statusService;
        this.manager.setName("HttpListener" + ++threadId);
    }

    // poor-man's IOC for simplicity

    public HttpListener(int port, int backlog) throws IOException {
        this(new ServerSocketWrap(port, backlog),
                new ThreadPoolWrapper(Executors.newCachedThreadPool()),
                new Router(),
                new OutputLogger(new TabLogFormatter()),
                new HttpStatusService());
    }

    public HttpListener(int port) throws IOException {
        this(new ServerSocketWrap(port),
                new ThreadPoolWrapper(Executors.newCachedThreadPool()),
                new Router(),
                new OutputLogger(new TabLogFormatter()),
                new HttpStatusService());
    }

    public HttpListener() throws IOException {
        this(80);
    }

    public HttpListener(int port, Logger logger) throws IOException {
        this(new ServerSocketWrap(port),
                new ThreadPoolWrapper(Executors.newCachedThreadPool()),
                new Router(),
                logger,
                new HttpStatusService());
    }

    public HttpListener(int port, int backlog, Logger logger) throws IOException {
        this(new ServerSocketWrap(port, backlog),
                new ThreadPoolWrapper(Executors.newCachedThreadPool()),
                new Router(),
                logger,
                new HttpStatusService());
    }


    public Router getRouter() { return this.router; }

    @Override
    public void handleRequest(Socket socket) {
        logger.log("Connection Accepted");
        try {
            HttpRequestReader reader = new HttpRequestReader(
                    new HttpMessageReader(socket.getInputStream()));
            router.route(reader.readRequest()).write(socket.getOutputStream());
            logger.log("successful response sent");

        } catch (IOException e) {
            logger.log(e);
        } catch (HttpRequestParseException e) {
            try {
                new HttpResponseFactory(statusService)
                        .status(HttpStatusCode.BAD_REQUEST)
                        .contentType(MimeType.html)
                        .protocol("http")
                        .version("1.1")
                        .body(new HttpResponseBody() {
                            String builder = "<!DOCTYPE html>\r\n" +
                                    "<html>\r\n" +
                                    "<head>\r\n" +
                                    "<title>Bad Request!</title>\r\n" +
                                    "</head>\r\n<body>\r\n" +
                                    "<h1>Bad Request</h1>\r\n" +
                                    "<p>Bad Request Received</p>\r\n" +
                                    "</body>\r\n</html>";
                            ByteArrayInputStream stream = new ByteArrayInputStream(builder.getBytes());
                            long size = builder.getBytes().length;
                            @Override
                            public InputStream getBodyInputStream() {

                                return (new ByteArrayInputStream(builder.getBytes()));
                            }

                            @Override
                            public long getSize() { return size; }

                        }).build().write(socket.getOutputStream());
                logger.log("Send Bad Request Response");
            } catch(IOException ex) {
                logger.log(ex);
            }
        } finally{
            try {
                socket.close();
                logger.log("Connection Closed");
            } catch(IOException e) {
                // nothing to do
            }
        }
    }
}
