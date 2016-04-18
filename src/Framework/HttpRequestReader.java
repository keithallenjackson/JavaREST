package Framework;

import com.sun.istack.internal.NotNull;

import java.io.*;

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
public class HttpRequestReader {

    private HttpMessageReader reader;

    public HttpRequestReader(@NotNull HttpMessageReader reader) {
        this.reader = reader;
    }

    public HttpRequest readRequest() throws IOException, HttpRequestParseException {
        try {

            HttpRequestFactory factory = new HttpRequestFactory();
            String totalRequest = reader.readHttpMessage();

            String[] lines = totalRequest.split("(\r\n|\r|\n)");

            String topLine = lines[0];

            String[] topLineParts = topLine.split(" ");

            //perform validation on top line parts
            if(topLineParts.length != 3) throw new HttpRequestParseException();

            factory.verb(topLineParts[0]).uri(topLineParts[1]).protocolAndVersion(topLineParts[2]);

            for (int i = 1; i < lines.length; i++) {
                String[] headerPieces = lines[i].split(":");
                String header = headerPieces[0];
                String[] values = headerPieces[1].split(",");

                if(header.equalsIgnoreCase("Host")) {
                    factory.hostname(values[0]);
                }
                else {
                    factory.header(header, values);
                }
            }


            return factory.build();
        }catch(IllegalArgumentException e) {
            throw new HttpRequestParseException();
        }
    }

    private Verb getVerbFromString(String verb) {
        String fixedVerb = verb.toUpperCase().trim();

        return Verb.valueOf(fixedVerb);
    }
}
