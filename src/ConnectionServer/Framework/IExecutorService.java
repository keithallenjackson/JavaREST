package ConnectionServer.Framework;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/30/2016
 * File:
 * Description:
 */
public interface IExecutorService {
    void execute(Runnable run);
    void shutdown();
    void awaitTermination(long time, TimeUnit unit) throws InterruptedException;
    List<Runnable> shutdownNow();
}
