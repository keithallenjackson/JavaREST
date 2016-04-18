package Framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class HttpResponse {
    private String protocol;
    private String version;
    private HttpStatus status;
    private MimeType contentType;
    private Map<String, List<String>> headers;
    private HttpResponseBody body;

    public HttpResponse(String protocol, String version, HttpStatus status, MimeType contentType,
                        Map<String, List<String>> headers, HttpResponseBody body) {
        this.protocol = protocol;
        this.version = version;
        this.status = status;
        this.headers = headers;
        this.body = body;
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Status line
        builder.append(protocol);
        builder.append("/");
        builder.append(version);
        builder.append(" ");
        builder.append(status.statusCode());
        builder.append(" ");
        builder.append(status.message());
        builder.append("\r\n");

        // built-in headers
        builder.append("Date: ");
        builder.append(new Date());
        builder.append("\r\n");

        builder.append("Server: JavaREST/1.0\r\n");

        builder.append("Connection: Closed\r\n");



        if(body != null) {
            contentType = contentType == null ? MimeType.bin : contentType;

            builder.append("Content-Type: ");
            builder.append(contentType.contentType);
            builder.append("\r\n");

            if(!isChunkedEncoding()) {
                builder.append("Content-Length: ");
                builder.append(body.getSize());
            } else {
                builder.append("Transfer-Encoding: chunked");
            }

        }
        builder.append("\r\n");


        // other headers
        for(Map.Entry<String, List<String>> entry : headers.entrySet()) {
            builder.append(entry.getKey());
            builder.append(": ");

            boolean oneAdded = false;
            for(String value : entry.getValue()) {
                if(value != null) {
                    value = value.trim();
                    if(!value.isEmpty()) {
                        if(oneAdded) builder.append(", ");
                        builder.append(value);
                        oneAdded = true;
                    }
                }
            }
            builder.append("\r\n");
        }

        return builder.toString();

    }

    private boolean isChunkedEncoding() {
        return body != null && body.getBodyInputStream() != null && body.getSize() < 0;
    }

    public void write(OutputStream out) throws IOException{
        out.write(toString().getBytes());
        out.write("\r\n".getBytes());
        if(isChunkedEncoding()) {
            writeBodyChunked(out);
        } else {
            writeBody(out);
        }

        out.write("\r\n".getBytes());
    }

    private void writeBody(OutputStream out) throws IOException{
        byte[] buffer = new byte[4096];
        int read;
        InputStream stream = body.getBodyInputStream();
        long wrote = 0;
        while((read = stream.read(buffer, 0, buffer.length)) >= 0 && wrote < body.getSize()) {
            if(wrote + read >= body.getSize()) {
                out.write(buffer, 0, (int) (body.getSize() - wrote));
                wrote += body.getSize() - wrote;
            } else {
                out.write(buffer, 0, read);
                wrote += read;
            }
        }

        out.write("\r\n".getBytes());
    }

    private void writeBodyChunked(OutputStream out) throws IOException{

        byte[] buffer = new byte[4096];
        int read;
        InputStream stream = body.getBodyInputStream();

        while((read = stream.read(buffer, 0, buffer.length)) >= 0) {
            out.write(Integer.toHexString(read).getBytes());
            out.write("\r\n".getBytes());

            out.write(buffer, 0, read);

            out.write("\r\n".getBytes());
        }

        out.write(Integer.toHexString(0).getBytes());

        out.write("\r\n".getBytes());
    }

}
