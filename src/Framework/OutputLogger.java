package Framework;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/5/2016
 * File:
 * Description:
 */
public class OutputLogger extends Logger {
    public OutputLogger(LogFormatter formatter) {
        super(System.out, formatter);
    }
}
