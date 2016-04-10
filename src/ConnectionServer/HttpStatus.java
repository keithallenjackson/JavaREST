package ConnectionServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/6/2016
 * File:
 * Description:
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
