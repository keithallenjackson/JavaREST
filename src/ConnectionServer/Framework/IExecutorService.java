package ConnectionServer.Framework;

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
}
