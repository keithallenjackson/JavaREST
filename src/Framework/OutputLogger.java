package Framework;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */

public class OutputLogger extends Logger {
    public OutputLogger(LogFormatter formatter) {
        super(System.out, formatter);
    }
}
