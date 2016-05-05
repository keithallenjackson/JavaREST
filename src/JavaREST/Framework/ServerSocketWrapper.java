package JavaREST.Framework;

import java.io.IOException;
import java.net.Socket;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */

public interface ServerSocketWrapper {
    Socket accept() throws IOException;
    boolean isClosed();
    void close() throws IOException;

}
