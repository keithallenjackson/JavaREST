package Framework;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
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
