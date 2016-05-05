package JavaREST.Framework;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public abstract class Listener implements Runnable {

    private ServerSocketWrapper wrapper;
    private IExecutorService executor;
    private Logger logger;
    private boolean shouldRun = false;
    protected Thread manager;

    public Listener(@NotNull ServerSocketWrapper wrapper,
                    @NotNull IExecutorService executor,
                    @NotNull Logger logger) {
        this.wrapper = wrapper;
        this.executor = executor;
        this.logger = logger;
        this.manager = new Thread(this);
    }

    @Override
    public void run() {
        shouldRun = true;
        while(!Thread.interrupted() && shouldRun) {
            try {
                Socket socket = wrapper.accept();
                executor.execute(() -> handleRequest(socket));
            } catch(IOException e) {
                shouldRun = false;
            }
        }

        logger.log(this.manager.getName() + " Shutting Down");

        executor.shutdown();

        try {
            executor.awaitTermination(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e1) {
            executor.shutdownNow();
        }

        try {
            wrapper.close();
        } catch(IOException er) {
            // Did due diligence
        }
    }

    public void shutdown() {
        try {
            shouldRun = false;
            wrapper.close();
        } catch(IOException e) {
            // do nothing
        } finally {
            executor.shutdown();

            try {
                executor.awaitTermination(3000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        try {
            manager.join(5000);
        } catch(InterruptedException e) {
            logger.log(e);
        }
    }

    public abstract void handleRequest(Socket socket);

    public void start() {
        manager.start();
    }
}
