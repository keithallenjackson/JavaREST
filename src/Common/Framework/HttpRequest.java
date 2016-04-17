package Common.Framework;

import Common.Framework.Verb;

import java.net.URI;
import java.util.Map;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/30/2016
 * File:
 * Description:
 */
public interface HttpRequest {
    Verb getVerb();
    URI getUri();
    String getHostname();
    String getProtocol();
    String getVersion();
    Map<String, String[]> getHeaders();
}
