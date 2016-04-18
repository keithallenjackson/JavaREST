package Wrappers;

import Framework.ServerSocketWrapper;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

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
public class ServerSocketWrap extends ServerSocket implements ServerSocketWrapper{
    public ServerSocketWrap(int port) throws IOException {
        super(port);
    }

    public ServerSocketWrap(int port, int backlog) throws IOException {
        super(port, backlog);
    }

    public ServerSocketWrap(int port, int backlog, @NotNull InetAddress bindAddress) throws IOException {
        super(port, backlog, bindAddress);
    }
}
