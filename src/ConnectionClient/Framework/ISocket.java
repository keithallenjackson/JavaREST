package ConnectionClient.Framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Future;

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
public interface ISocket {
    void connect() throws IOException;
    Future<Boolean> connectAsync() throws IOException;
    void disconnect() throws IOException;
    Future<Boolean> disconnectAsync() throws IOException;
    InputStream getInputStream() throws IOException;
    OutputStream getOutputStream() throws IOException;
}
