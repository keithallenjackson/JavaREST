package ConnectionClient.Wrappers;

import ConnectionClient.Framework.IConnectionInformation;
import ConnectionClient.Framework.IConnectionMetaInformation;
import ConnectionClient.Framework.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class SocketWrapper implements ISocket {
    private Socket socket;
    private IConnectionInformation info;
    private IConnectionMetaInformation meta;
    private ThreadPoolExecutor executor;
    private Logger logger;

    @Override
    public synchronized void connect() throws IOException {
        if(socket != null) {
            disconnect();
        }

        socket = new Socket(info.getAddress(), info.getPort());

    }

    @Override
    public Future<Boolean> connectAsync() throws IOException {

        return executor.submit( () -> {
                connect();
                return true;
        });
    }

    @Override
    public synchronized void disconnect() throws IOException {
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.INFO, "IOException in disconnect. IConnection already disconnected");
            }
        }
        socket = null;
    }

    @Override
    public Future<Boolean> disconnectAsync() throws IOException {
        return executor.submit(() -> {
            disconnect();
            return true;
        });
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }
}
