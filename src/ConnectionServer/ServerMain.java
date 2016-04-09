package ConnectionServer;

import Common.Logger;
import Common.OutputLogger;
import Common.TabLogFormatter;
import ConnectionServer.Wrappers.ServerSocketWrap;
import ConnectionServer.Wrappers.ThreadPoolWrapper;
import com.sun.deploy.xml.XMLParser;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        Listener listener = new Listener(new ServerSocketWrap(9001),
                (socket) -> {
                    try {
                        BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        writer.write("Connected! Bye bye!");
                        writer.flush();

                    } catch (IOException e) {
                        logger.log(e);
                    } finally {
                        try {
                            socket.close();
                        } catch(IOException e) {
                            // nothing to do
                        }
                    }

                }, new ThreadPoolWrapper(Executors.newCachedThreadPool()));

        listener.start();
        waitForChar('q');
        listener.interrupt();


    }

    public static void waitForChar(char c) {

        try {
            int read;
            while ((read = System.in.read()) > -1 && read != c) ;
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
