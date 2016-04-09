package ConnectionClient;

import ConnectionClient.Framework.*;
import com.sun.istack.internal.NotNull;

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
public class TcpIConnection implements IConnection {

    private final IConnectionInformation info;
    private final IConnectionMetaInformation meta;
    private final IFactory<ISocket> factory;
    private ISocket wrapper;

    public TcpIConnection(@NotNull IConnectionInformation info,
                          @NotNull IConnectionMetaInformation meta,
                          @NotNull IFactory<ISocket> factory) {


        this.info = info;
        this.meta = meta;
        this.factory = factory;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return wrapper.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return wrapper.getInputStream();
    }

    @Override
    public void disconnect() throws IOException {
        wrapper.disconnect();
    }

    @Override
    public void connect() throws IOException {
        if(wrapper != null) {
            try {
                wrapper.disconnect();
            } catch(IOException e) {

            }
        }
        wrapper = factory.create();
    }

}
