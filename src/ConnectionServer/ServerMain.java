package ConnectionServer;

import Common.*;
import ConnectionServer.Framework.HttpRequestParseException;
import ConnectionServer.Wrappers.HttpStatusService;
import ConnectionServer.Wrappers.ServerSocketWrap;
import ConnectionServer.Wrappers.ThreadPoolWrapper;
import com.sun.deploy.xml.XMLParser;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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

        Listener listener = new Listener(new ServerSocketWrap(9001),
                (socket) -> {
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

                }, new ThreadPoolWrapper(Executors.newCachedThreadPool()));

        listener.start();
        waitForChar('q');
        listener.interrupt();


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
