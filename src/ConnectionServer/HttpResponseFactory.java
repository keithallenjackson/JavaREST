package ConnectionServer;

import Common.MimeType;

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
public class HttpResponseFactory {
    private String protocol = "HTTP",
            version = "1.1";
    private HttpStatusCode status;
    private MimeType contentType = MimeType.text;
    private HttpResponseBody body;

    public HttpResponseFactory protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public HttpResponseFactory version(String version) {
        this.version = version;
        return this;
    }

    public HttpResponseFactory status(HttpStatusCode status) {
        this.status = status;
        return this;
    }

    public HttpResponseFactory contenType(MimeType type) {
        contentType = type;
        return this;
    }

    public HttpResponseFactory body(HttpResponseBody body) {
        this.body = body;
        return this;
    }


    // build
    public IHttpResponse

}
