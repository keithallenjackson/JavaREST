package JavaREST.Framework;

import java.io.InputStream;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public abstract class HttpResponseBody {

    public abstract InputStream getBodyInputStream();

    public long getSize() {
        return -1;
    }
}
