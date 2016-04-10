package Common;

import java.io.InputStream;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/9/2016
 * File:
 * Description:
 */
public abstract class HttpResponseBody {

    public abstract InputStream getBodyInputStream();

    public long getSize() {
        return -1;
    }
}
