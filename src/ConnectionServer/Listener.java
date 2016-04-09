package ConnectionServer;

import ConnectionServer.Framework.IExecutorService;
import ConnectionServer.Framework.ServerSocketWrapper;
import ConnectionServer.Framework.OnAcceptListener;
import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;


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
public class Listener extends Thread {

    private ServerSocketWrapper wrapper;
    private OnAcceptListener handler;
    private IExecutorService executor;

    public Listener(@NotNull ServerSocketWrapper wrapper,
                    @NotNull OnAcceptListener handler,
                    @NotNull IExecutorService executor) {
        this.wrapper = wrapper;
        this.handler = handler;
        this.executor = executor;
    }

    @Override
    public void run() {
        boolean exceptionOccurred = false;
        while(!Thread.interrupted() && !exceptionOccurred) {
            try {
                Socket socket = wrapper.accept();
                executor.execute(() -> handler.Handle(socket));
            } catch(IOException e) {
                exceptionOccurred = true;
                executor.shutdown();

                try {
                    wrapper.close();
                } catch(IOException er) {
                    // Did due diligence
                }
            }
        }
    }

}
