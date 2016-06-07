package JavaREST.Framework;

import java.io.*;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */

public class HttpMessageReader extends BufferedReader {

    public HttpMessageReader(InputStream in) {
        super(new InputStreamReader(in));
    }

    public String readHttpMessage() throws IOException{
        StringBuilder builder = new StringBuilder();

        String line;
        int emptyLines = 0;
        while(emptyLines < 1 && (line = readLine()) != null) {
            if(line.isEmpty())
                emptyLines++;
            else {
                builder.append(line);
                builder.append("\r\n");
            }

        }

        return builder.toString();
    }

    public InputStream readBodyChunked() throws IOException {
        int amount = Integer.decode("0x" + readLine());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while(amount > 0) {
            int read;
            int remaining = amount;
            while((read = read()) > -1 && remaining > 0) {
                out.write((byte) read);
                remaining--;
            }

            if(read == -1) break;

            readLine(); // get to next line for next amount

            amount = Integer.decode("0x" + readLine());
        }
        readLine(); // end of chunked encoding

        return new ByteArrayInputStream(out.toByteArray());
    }

    public InputStream readBody(int size) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int remaining = size;
        int read;
        while(remaining > 0 && (read = read()) > 0) {
            out.write((byte)read);
            remaining--;
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
