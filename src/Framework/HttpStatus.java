package Framework;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class HttpStatus {

    private int statusCode;
    private String message;

    public HttpStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int statusCode() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return this.statusCode == ((HttpStatus) obj).statusCode();
        } catch(ClassCastException|NullPointerException e) {
            return false;
        }
    }
}
