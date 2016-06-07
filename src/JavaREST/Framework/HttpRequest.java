package JavaREST.Framework;


import java.io.InputStream;
import java.net.URI;
import java.util.Map;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public interface HttpRequest {
    Verb getVerb();
    URI getUri();
    String getHostname();
    String getProtocol();
    String getVersion();
    Map<String, String[]> getHeaders();
    InputStream getBody();
    MimeType getContentType();
}
