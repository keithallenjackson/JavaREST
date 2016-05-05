package JavaREST.Framework;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */

public interface IExecutorService {
    void execute(Runnable run);
    void shutdown();
    void awaitTermination(long time, TimeUnit unit) throws InterruptedException;
    List<Runnable> shutdownNow();
}
