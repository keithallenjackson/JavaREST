package ConnectionServer;

import Common.*;
import ConnectionServer.Wrappers.HttpStatusService;
import ConnectionServer.Wrappers.ServerSocketWrap;
import ConnectionServer.Wrappers.ThreadPoolWrapper;

import java.io.*;
import java.util.concurrent.Executors;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/31/2016
 * File:
 * Description:
 */
public class ServerMain {
    public static void main(String[] args) throws IOException {

        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;


        Logger logger = new OutputLogger(new TabLogFormatter());

        Router router = new Router();
        HttpStatusService statusService = new HttpStatusService();

        router.add("//", request -> {
            HttpResponseFactory factory = new HttpResponseFactory(statusService);
            return factory.status(HttpStatusCode.OK).protocol("http").version("1.1").contentType(MimeType.html)
                    .body(new HttpResponseBody() {
                        String builder = "<!DOCTYPE html>\r\n" +
                                "<html>\r\n" +
                                "<head>\r\n" +
                                "<title>HomePage</title>\r\n" +
                                "</head>\r\n<body>\r\n" +
                                "<h1>Default Landing Page</h1>\r\n" +
                                "<p>Welcome to REST...</p>\r\n" +
                                "</body>\r\n</html>";
                        long size = builder.getBytes().length;
                        ByteArrayInputStream stream = new ByteArrayInputStream(builder.getBytes());
                        @Override
                        public InputStream getBodyInputStream() {

                            return stream;
                        }

                        @Override
                        public long getSize() {
                            return size;
                        }
                    }).build();
        });

        HttpListener listener = new HttpListener(new ServerSocketWrap(port),
                new ThreadPoolWrapper(Executors.newCachedThreadPool()),
                router,
                logger,
                statusService);

        listener.start();
        waitForChar('q');
        logger.log("Shutting Down");
        listener.shutdown();

    }

    private static void waitForChar(char c) {

        try {
            int read;
            while ((read = System.in.read()) > -1 && read != c) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
