package ConnectionServer.Framework;

import java.io.IOException;
import java.net.Socket;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/28/2016
 * File:
 * Description:
 */
public interface ServerSocketWrapper {
    Socket accept() throws IOException;
    boolean isClosed();
    void close() throws IOException;

}
