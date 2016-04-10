package ConnectionServer;

import java.io.*;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/31/2016
 * File:
 * Description:
 */
public class HttpMessageReader extends BufferedReader {

    public HttpMessageReader(InputStream in) {
        super(new InputStreamReader(in));
    }

    public String readHttpMessage() throws IOException{
        StringBuilder builder = new StringBuilder();

        String line;
        int emptyLines = 0;
        while((line = readLine()) != null && emptyLines < 2) {
            if(line.isEmpty())
                emptyLines++;
            else
                builder.append(line);
        }

        return builder.toString();
    }
}
