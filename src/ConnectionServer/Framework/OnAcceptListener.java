package ConnectionServer.Framework;

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
@FunctionalInterface
public interface OnAcceptListener {
    void Handle(Socket socket);
}
