package Resources;

import Framework.*;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class AudioResource {

    @HttpVerb(value = Verb.GET)
    public HttpResponse getAudio(int id) {
        HttpResponseFactory factory = new HttpResponseFactory(new HttpStatusService());

        try {
            factory.version("1.1").protocol("http").status(HttpStatusCode.OK)
                    .body(new AudioResponseBody("\\out\\production\\RestFulWebService\\Example\\test" + id + ".mp3")).contentType(MimeType.mp3);
        } catch(FileNotFoundException e) {
            factory.version("1.1").protocol("http").status(HttpStatusCode.NOT_FOUND).contentType(MimeType.html)
                    .body(new HtmlResponseBody("404 Not Found",
                            "<h1>404 Not Found</h1><p>You requested something we don't have...</p>"));
        } catch(IOException e) {
            factory.version("1.1").protocol("http").status(HttpStatusCode.INTERNAL_SERVER_ERROR).contentType(MimeType.html)
                    .body(new HtmlResponseBody("Error!",
                            "<h1>Internal Server Error</h1><p>Somethine went terribly wrong...</p>"));
        }


        return factory.build();
    }
}
