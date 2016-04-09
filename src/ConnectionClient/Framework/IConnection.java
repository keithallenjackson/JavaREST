package ConnectionClient.Framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
public interface IConnection {
    OutputStream getOutputStream() throws IOException;
    InputStream getInputStream() throws IOException;
    void disconnect() throws IOException;
    void connect() throws IOException;
}
