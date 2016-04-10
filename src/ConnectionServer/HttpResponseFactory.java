package ConnectionServer;

import Common.HttpResponse;
import Common.HttpResponseBody;
import Common.MimeType;
import ConnectionServer.Wrappers.HttpStatusService;
import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.InvalidArgumentException;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private HttpStatus status;
    private MimeType contentType = MimeType.text;
    private HttpResponseBody body;
    private HttpStatusService statusService;
    private Map<String, List<String>> headers;

    public HttpResponseFactory(@NotNull HttpStatusService statusService) {
        this.statusService = statusService;
        this.headers = new LinkedHashMap<>();
    }

    public HttpResponseFactory protocol(String protocol) throws InvalidArgumentException {
        if(protocol == null || protocol.isEmpty()) throw new InvalidArgumentException(new String[] {"protocol"});
        this.protocol = protocol;
        return this;
    }

    public HttpResponseFactory version(@NotNull String version) throws InvalidArgumentException{
        if(version.isEmpty()) throw new InvalidArgumentException(new String[] {"version"});
        this.version = version;
        return this;
    }

    public HttpResponseFactory status(@NotNull HttpStatus status) {
        this.status = status;
        return this;
    }

    public HttpResponseFactory status(HttpStatusCode code) throws InvalidArgumentException {
        HttpStatus httpStatus = statusService.get(code);
        if(httpStatus == null) throw new InvalidArgumentException(new String[] {"code"});
        return status(httpStatus);
    }

    public HttpResponseFactory contentType(@NotNull MimeType type) {
        contentType = type;
        return this;
    }

    public HttpResponseFactory body(@NotNull HttpResponseBody body) {
        this.body = body;
        return this;
    }

    public HttpResponseFactory header(String header, String... values) {
        headers.putIfAbsent(header, new LinkedList<>());
        for(String value : values)
            headers.get(header).add(value);

        return this;
    }


    // build
    public HttpResponse build() throws InvalidStateException {
        // validation
        if(protocol == null || protocol.isEmpty())
            throw new InvalidStateException("Variable 'protocol' cannot be empty or null");
        if(version == null || version.isEmpty())
            throw new InvalidStateException("Variable 'version' cannot be empty or null");
        if(this.status == null) throw new InvalidStateException("Variable 'status' cannot be null");
        if(this.contentType == null) throw new InvalidStateException("Variable 'contentType cannot be null");

        // creation
        return new HttpResponse(protocol, version, status, contentType, headers, body);
    }

}
