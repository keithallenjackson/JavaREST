package Framework;

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
}
