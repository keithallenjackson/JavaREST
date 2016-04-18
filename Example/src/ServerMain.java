import Framework.*;
import Resources.AdderResource;
import Resources.AudioResource;
import Resources.HtmlResponseBody;

import java.io.*;

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
        HttpListener listener = new HttpListener(port, logger);

        listener.getRouter().add(new ResourceRoute("/audio/{id}", new AudioResource()));
        listener.getRouter().add(new ResourceRoute("/adder/{number1}/{number2}", new AdderResource()));

        listener.getRouter().add(new FunctionRoute("//", request -> {
            HttpResponseFactory factory = new HttpResponseFactory();
            return factory.status(HttpStatusCode.OK).protocol("http").version("1.1").contentType(MimeType.html)
                    .body(new HtmlResponseBody("HomePage", "<h1>Default Landing Page</h1>\r\n<p>Welcome to REST...</p>\r\n"))
                    .build();
        }));

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
