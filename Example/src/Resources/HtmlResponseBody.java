package Resources;

import Framework.HttpResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class HtmlResponseBody extends HttpResponseBody {

    ByteArrayInputStream stream;

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
