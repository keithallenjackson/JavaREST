package ConnectionServer.Wrappers;

import ConnectionServer.Framework.IExecutorService;

import java.util.concurrent.ExecutorService;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/31/2016
 * File:
 * Description:
 */
public class ThreadPoolWrapper implements IExecutorService {


    private ExecutorService service;

    public ThreadPoolWrapper(ExecutorService service) {
        this.service = service;
    }

    @Override
    public void execute(Runnable run) {
        service.execute(run);
    }

    @Override
    public void shutdown() {
        service.shutdown();
    }
}
