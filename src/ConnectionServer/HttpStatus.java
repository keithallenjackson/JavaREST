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

    private HttpStatusCode code;
    private int value;

    private HttpStatus(HttpStatusCode code, int value ){
        this.code = code;
        this.value = value;
    }

    public HttpStatusCode getStatusCode() {return code;}
    public int getValue() { return value; }

    private static Map<Integer, HttpStatusCode> statusCodeMap;
    private static Map<Integer, HttpStatus> statusInstanceMap;

    static {
        statusCodeMap = new HashMap<>();
        statusInstanceMap = new HashMap<>();

        statusCodeMap.put(200, HttpStatusCode.OK);
        statusCodeMap.put(100, HttpStatusCode.CONTINUE);
        statusCodeMap.put(101, HttpStatusCode.SWITCHING_PROTOCOLS);
        statusCodeMap.put(201, HttpStatusCode.CREATED);
        statusCodeMap.put(202, HttpStatusCode.ACCEPTED);
        statusCodeMap.put(203, HttpStatusCode.NON_AUTHORITATIVE_INFORMATION);
        statusCodeMap.put(204, HttpStatusCode.NO_CONTENT);
        statusCodeMap.put(205, HttpStatusCode.RESET_CONTENT);
        statusCodeMap.put(206, HttpStatusCode.PARTIAL_CONTENT);
        statusCodeMap.put(300, HttpStatusCode.MULTIPLE_CHOICES);
        statusCodeMap.put(301, HttpStatusCode.MOVED_PERMANENTLY);
        statusCodeMap.put(302, HttpStatusCode.FOUND);
        statusCodeMap.put(303, HttpStatusCode.SEE_OTHER);
        statusCodeMap.put(304, HttpStatusCode.NOT_MODIFIED);
        statusCodeMap.put(305, HttpStatusCode.USE_PROXY);
        statusCodeMap.put(307, HttpStatusCode.TEMPORARY_REDIRECT);
        statusCodeMap.put(400, HttpStatusCode.BAD_REQUEST);
        statusCodeMap.put(401, HttpStatusCode.UNAUTHORIZED);
        statusCodeMap.put(402, HttpStatusCode.PAYMENT_REQUIRED);
        statusCodeMap.put(403, HttpStatusCode.FORBIDDEN);
        statusCodeMap.put(404, HttpStatusCode.NOT_FOUND);
        statusCodeMap.put(405, HttpStatusCode.METHOD_NOT_FOUND);
        statusCodeMap.put(406, HttpStatusCode.NOT_ACCEPTABLE);
        statusCodeMap.put(407, HttpStatusCode.PROXY_AUTHENTICATION_REQUIRED);
        statusCodeMap.put(408, HttpStatusCode.REQUEST_TIMEOUT);
        statusCodeMap.put(409, HttpStatusCode.CONFLICT);
        statusCodeMap.put(410, HttpStatusCode.GONE);
        statusCodeMap.put(411, HttpStatusCode.LENGTH_REQUIRED);
        statusCodeMap.put(412, HttpStatusCode.PRECONDITION_FAILED);
        statusCodeMap.put(413, HttpStatusCode.REQUEST_ENTITY_TOO_LARGE);
        statusCodeMap.put(414, HttpStatusCode.REQUEST_URI_TOO_LONG);
        statusCodeMap.put(415, HttpStatusCode.UNSUPPORTED_MEDIA_TYPE);
        statusCodeMap.put(416, HttpStatusCode.REQUESTED_RANGE_NOT_SATISFIABLE);
        statusCodeMap.put(417, HttpStatusCode.EXPECTATION_FAILED);
        statusCodeMap.put(500, HttpStatusCode.INTERNAL_SERVER_ERROR);
        statusCodeMap.put(501, HttpStatusCode.NOT_IMPLEMENTED);
        statusCodeMap.put(502, HttpStatusCode.BAD_GATEWAY);
        statusCodeMap.put(503, HttpStatusCode.SERVICE_UNAVAILABLE);
        statusCodeMap.put(504, HttpStatusCode.GATEWAY_TIMEOUT);
        statusCodeMap.put(505, HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED);

        for(Integer value : statusCodeMap.keySet()) {
            statusInstanceMap.put(value, new HttpStatus(statusCodeMap.get(value), value));
        }

    }

    public static HttpStatus getByStatusCode(HttpStatusCode code) {
        if (statusCodeMap.containsValue(code)) {
            Set<Map.Entry<Integer, HttpStatusCode>> set = statusCodeMap.entrySet();
            for (int i = 0; i < set.size(); i++) {
                Map.Entry<Integer, HttpStatusCode> entry = (Map.Entry<Integer, HttpStatusCode>) set.toArray()[i];
                if (entry.getValue() == code) {
                    return statusInstanceMap.get(entry.getKey());
                }
            }
        }
        return null;
    }

    public static HttpStatus getByValue(int value) {
        return statusInstanceMap.get(value);
    }
}
