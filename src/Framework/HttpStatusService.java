package Framework;

import java.util.LinkedHashMap;
import java.util.Map;

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
public class HttpStatusService {

    private Map<HttpStatusCode, HttpStatus> map = new LinkedHashMap<>();

    public HttpStatusService() {
        map.put(HttpStatusCode.OK, new HttpStatus(200, "OK"));
        map.put(HttpStatusCode.CONTINUE, new HttpStatus(100, "Continue"));
        map.put(HttpStatusCode.SWITCHING_PROTOCOLS, new HttpStatus(101, "Switching Protocols"));
        map.put(HttpStatusCode.CREATED, new HttpStatus(201, "Created"));
        map.put(HttpStatusCode.ACCEPTED, new HttpStatus(202, "Accepted"));
        map.put(HttpStatusCode.NON_AUTHORITATIVE_INFORMATION, new HttpStatus(203, "Non-Authoritative Information"));
        map.put(HttpStatusCode.NO_CONTENT, new HttpStatus(204, "No Content"));
        map.put(HttpStatusCode.RESET_CONTENT, new HttpStatus(205, "Reset Content"));
        map.put(HttpStatusCode.PARTIAL_CONTENT, new HttpStatus(206, "Partial Content"));
        map.put(HttpStatusCode.MULTIPLE_CHOICES, new HttpStatus(300, "Multiple Choices"));
        map.put(HttpStatusCode.MOVED_PERMANENTLY, new HttpStatus(301, "Moved Permanently"));
        map.put(HttpStatusCode.FOUND, new HttpStatus(302, "Found"));
        map.put(HttpStatusCode.SEE_OTHER, new HttpStatus(303, "See Other"));
        map.put(HttpStatusCode.NOT_MODIFIED, new HttpStatus(304, "Not Modified"));
        map.put(HttpStatusCode.USE_PROXY, new HttpStatus(305, "Switch Proxy"));
        map.put(HttpStatusCode.TEMPORARY_REDIRECT, new HttpStatus(307, "Temporary Redirect"));
        map.put(HttpStatusCode.RESUME_INCOMPLETE, new HttpStatus(308, "Resume Incomplete"));
        map.put(HttpStatusCode.BAD_REQUEST, new HttpStatus(400, "Bad Request"));
        map.put(HttpStatusCode.UNAUTHORIZED, new HttpStatus(401, "Unauthorized"));
        map.put(HttpStatusCode.PAYMENT_REQUIRED, new HttpStatus(402, "Payment Required"));
        map.put(HttpStatusCode.FORBIDDEN, new HttpStatus(403, "Forbidden"));
        map.put(HttpStatusCode.NOT_FOUND, new HttpStatus(404, "Not Found"));
        map.put(HttpStatusCode.METHOD_NOT_FOUND, new HttpStatus(405, "Method Not Allowed"));
        map.put(HttpStatusCode.NOT_ACCEPTABLE, new HttpStatus(406, "Not Acceptable"));
        map.put(HttpStatusCode.PROXY_AUTHENTICATION_REQUIRED, new HttpStatus(407, "Proxy Authentication Required"));
        map.put(HttpStatusCode.REQUEST_TIMEOUT, new HttpStatus(408, "Request Timeout"));
        map.put(HttpStatusCode.CONFLICT, new HttpStatus(409, "Conflict"));
        map.put(HttpStatusCode.GONE, new HttpStatus(410, "Gone"));
        map.put(HttpStatusCode.LENGTH_REQUIRED, new HttpStatus(411, "Length Required"));
        map.put(HttpStatusCode.PRECONDITION_FAILED, new HttpStatus(412, "Precondition Failed"));
        map.put(HttpStatusCode.REQUEST_ENTITY_TOO_LARGE, new HttpStatus(413, "Requrest Entity Too Large"));
        map.put(HttpStatusCode.REQUEST_URI_TOO_LONG, new HttpStatus(414, "Request-URI Too Long"));
        map.put(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE, new HttpStatus(415, "Unsupported Media Type"));
        map.put(HttpStatusCode.REQUESTED_RANGE_NOT_SATISFIABLE, new HttpStatus(416, "Requested Range Not Satisfiable"));
        map.put(HttpStatusCode.EXPECTATION_FAILED, new HttpStatus(417, "Expectation Failed"));
        map.put(HttpStatusCode.INTERNAL_SERVER_ERROR, new HttpStatus(500, "Internal Server Error"));
        map.put(HttpStatusCode.NOT_IMPLEMENTED, new HttpStatus(501, "Not Implemented"));
        map.put(HttpStatusCode.BAD_GATEWAY, new HttpStatus(502, "Bad Gateway"));
        map.put(HttpStatusCode.SERVICE_UNAVAILABLE, new HttpStatus(503, "Service unavailable"));
        map.put(HttpStatusCode.GATEWAY_TIMEOUT, new HttpStatus(504, "Gateway Timeout"));
        map.put(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED, new HttpStatus(505, "HTTP Version Not Supported"));
        map.put(HttpStatusCode.NETWORK_AUTHENTICATION_REQUIRED, new HttpStatus(511, "Network Authentication Required"));
    }

    public HttpStatus get(HttpStatusCode code) {
        return map.get(code);
    }

    public HttpStatus get(int codeNumber) {
        for( Map.Entry<HttpStatusCode, HttpStatus> pair : map.entrySet()) {
            if(pair.getValue().statusCode() == codeNumber) return pair.getValue();
        }
        return null;
    }
}
