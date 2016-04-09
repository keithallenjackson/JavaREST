package ConnectionServer;

import ConnectionServer.Framework.IRequest;
import ConnectionServer.Framework.HttpRequestParseException;
import Common.Framework.Verb;
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

    public IRequest readRequest() throws IOException, HttpRequestParseException {
        try {

            HttpRequestFactory factory = new HttpRequestFactory();
            String totalRequest = reader.readHttpMessage();

            String[] lines = totalRequest.split("(\r\n|\r|\n)");

            String topLine = lines[0];

            String[] topLineParts = topLine.split(" ");

            //perform validation on top line parts
            if(topLineParts.length != 3) throw new HttpRequestParseException();

            factory.verb(topLineParts[0]).uri(topLineParts[1]).protocolAndVersion(topLineParts[3]);



            Verb verb = getVerbFromString(totalRequest);
            return null;
        }catch(IllegalArgumentException e) {
            throw new HttpRequestParseException();
        }
    }

    private Verb getVerbFromString(String verb) {
        String fixedVerb = verb.toUpperCase().trim();

        return Verb.valueOf(fixedVerb);
    }
}
