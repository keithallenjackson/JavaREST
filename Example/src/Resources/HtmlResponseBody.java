package Resources;

import JavaREST.Framework.HttpResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class HtmlResponseBody extends HttpResponseBody {

    private ByteArrayInputStream stream;

    public HtmlResponseBody(String title, String html) {
        String builder = "<!DOCTYPE html>\r\n<html>\r\n<head>\r\n<title>" +
                title +
                "</title>\r\n</head><body>" +
                html +
                "</body>\r\n</html>";
        stream = new ByteArrayInputStream(builder.getBytes());
    }

    @Override
    public InputStream getBodyInputStream() {
        return stream;
    }
}
