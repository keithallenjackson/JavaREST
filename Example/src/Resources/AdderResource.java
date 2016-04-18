package Resources;

import Framework.HttpVerb;
import Framework.Verb;
import Framework.HttpResponse;
import Framework.HttpResponseFactory;
import Framework.HttpStatusCode;
import Framework.MimeType;
import Framework.HttpStatusService;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/18/2016
 * File:
 * Description:
 */
public class AdderResource {

    @HttpVerb(value = Verb.GET)
    public HttpResponse add(int one, int two) {
        return new HttpResponseFactory(new HttpStatusService())
                .contentType(MimeType.html)
                .status(HttpStatusCode.OK)
                .protocol("http")
                .version("1.1")
                .body(new HtmlResponseBody("Adder 1.0", "<p>" + one + " + " + two + " = " + (one + two) + "</p>"))
                .build();
    }
}
