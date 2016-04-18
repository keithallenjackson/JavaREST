package Framework;

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
public class HttpRequestParseException extends Exception {
    public HttpRequestParseException() {
        this("Not a valid request");
    }

    public HttpRequestParseException(String message) {
        super(message);
    }

    public HttpRequestParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestParseException(Throwable cause) {
        super(cause);
    }

    public HttpRequestParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
