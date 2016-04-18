package Framework;

import java.io.IOException;
import java.io.OutputStream;

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
public class Logger {

    private final OutputStream out;
    private final LogFormatter formatter;

    public Logger(OutputStream out, LogFormatter formatter) {

        this.out = out;
        this.formatter = formatter;
    }

    public void log(Class<?> T, String message) {
        String entry = formatter.Format(T, message);
        write(entry);
    }

    public void log(Object obj, String message) {
        String entry = formatter.Format(obj, message);
        write(entry);
    }

    public void log(Object obj) {
        log(obj, null);
    }

    protected void write(String entry) {
        byte[] entryBytes = (entry + "\r\n").getBytes();
        try {
            out.write(entryBytes);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        write(message);
    }
}
