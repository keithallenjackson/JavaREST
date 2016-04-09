package ConnectionClient;

import ConnectionClient.Framework.*;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

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
public class ConnectionFactory implements IFactory<IConnection> {
    private IConnectionInformation info;

    private IConnectionMetaInformation meta;

    protected IConnectionInitiator initiator;

    public ConnectionFactory() {
        this((info1, meta1) -> new IConnection() {
            Socket socket = null;
            @Override
            public OutputStream getOutputStream() throws IOException {
                return socket.getOutputStream();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return socket.getInputStream();
            }

            @Override
            public void disconnect() throws IOException {
                socket.close();
            }

            @Override
            public void connect() throws IOException {
                socket = new Socket(info1.getAddress(), info1.getPort());
                socket.setSoTimeout(meta1.getTimeout());
            }
        });
    }

    public ConnectionFactory(@NotNull IConnectionInitiator initiator) {
        this.initiator = initiator;

        meta = () -> 5000;
        info = new IConnectionInformation() {
            @Override
            public InetAddress getAddress() throws UnknownHostException{
                return Inet4Address.getLoopbackAddress();
            }

            @Override
            public int getPort() {
                return 50000;
            }
        };
    }

    @Override
    public IConnection create() {
        try {
            return initiator.Initialize(info, meta);
        } catch(IOException e) {
            return null;
        }
    }
}
