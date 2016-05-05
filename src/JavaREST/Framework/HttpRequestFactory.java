package JavaREST.Framework;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class HttpRequestFactory {
    private String verb, uri, protocol, version, hostname;
    private Map<String, String[]> headers;
    private InputStream body;

    public HttpRequestFactory() {
        headers = new LinkedHashMap<>();
    }

    public HttpRequestFactory verb(String verb) {
        this.verb = verb;
        return this;
    }

    public HttpRequestFactory verb(Verb verb) {
        this.verb = verb.name();
        return this;
    }

    public HttpRequestFactory uri(String uri) {
        this.uri = uri;
        return this;
    }

    public HttpRequestFactory protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public HttpRequestFactory version(String version) {
        this.version = version;
        return this;
    }

    public HttpRequestFactory hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public HttpRequestFactory header(String header, String[] values) {
        headers.put(header, values);
        return this;
    }

    public HttpRequestFactory protocolAndVersion(String protocolAndVersion) {
        String[] parts = protocolAndVersion.split("/");
        protocol(parts[0]).version(parts[1]);
        return this;
    }

    public HttpRequestFactory header(String headerString) throws HttpRequestParseException {

        String[] split = headerString.split(":");

        // validation
        if(split.length < 2) throw new HttpRequestParseException();

        String key = headerString.split(":")[0].trim();
        String[] values = headerString.split(":")[1].trim().split("(,|;)");

        if(key.isEmpty() || values.length < 1) throw new HttpRequestParseException();

        // cleanup values
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
        }

        headers.put(key, values);

        return this;
    }

    public HttpRequestFactory body(byte[] in) {
        this.body = new ByteArrayInputStream(in);
        return this;
    }

    public HttpRequestFactory body(InputStream in) {
        this.body = in;
        return this;
    }

    public boolean bodyIsExpected() {
        return chunkedEncodingIsDefined() || contentLength() > -1;
    }

    public boolean chunkedEncodingIsDefined() {
        return headers.get("Transfer-Encoding") != null;
    }

    public int contentLength() {
        return headers.get("Content-Length") != null ? Integer.parseInt(headers.get("Content-Length")[0]) : -1;
    }

    public HttpRequest build() throws HttpRequestParseException {
        if(verb == null || uri == null || protocol == null || version == null || hostname == null)
            throw new HttpRequestParseException("verb|uri|protocol|version|hostname cannot be null");

        if(bodyIsExpected() && body == null) throw new HttpRequestParseException("body is expected but was null");

        try {
            final Verb parsedVerb = Verb.valueOf(verb.toUpperCase().trim());
            final URI parsedUri = URI.create(uri.trim());


            return new HttpRequest() {
                @Override
                public Verb getVerb() {
                    return parsedVerb;
                }

                @Override
                public URI getUri() {
                    return parsedUri;
                }

                @Override
                public String getHostname() {
                    return hostname;
                }

                @Override
                public String getProtocol() {
                    return protocol;
                }

                @Override
                public String getVersion() {
                    return version;
                }

                @Override
                public Map<String, String[]> getHeaders() {
                    return headers;
                }

                @Override
                public InputStream getBody() {
                    return body;
                }

                @Override
                public MimeType getContentType() {
                    String[] acceptableTypes = headers.get("Content-Type");

                    return acceptableTypes == null ? null : MimeType.valueOf(acceptableTypes[0]);
                }

                @Override
                public String toString() {
                    StringBuilder builder = new StringBuilder();
                    builder.append(this.getVerb().name())
                            .append(" ")
                            .append(getUri().getPath())
                            .append(" ")
                            .append(protocol)
                            .append("/")
                            .append(version);
                    builder.append("\r\n");
                    builder.append("Hostname: ")
                            .append(getHostname());
                    builder.append("\r\n");

                    for(String key : headers.keySet()) {
                        builder.append(key)
                                .append(": ")
                                .append(String.join(", ", headers.get(key)).trim());
                        builder.append("\r\n");
                    }
                    builder.append("\r\n");

                    return builder.toString();
                }

                @Override
                public boolean equals(Object obj) {
                    return this.toString().contentEquals(obj.toString());
                }
            };
        } catch (IllegalArgumentException e) {
            throw new HttpRequestParseException();
        }
    }
}
