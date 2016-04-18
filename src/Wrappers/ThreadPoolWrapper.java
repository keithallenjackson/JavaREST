package Wrappers;

import Framework.IExecutorService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void awaitTermination(long time, TimeUnit unit) throws InterruptedException {
        service.awaitTermination(time, unit);
    }

    @Override
    public List<Runnable> shutdownNow() {
        return service.shutdownNow();
    }
}
