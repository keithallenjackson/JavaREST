package Resources;

import JavaREST.Framework.HttpVerb;
import JavaREST.Framework.Verb;
import JavaREST.Framework.HttpResponse;
import JavaREST.Framework.HttpResponseFactory;
import JavaREST.Framework.HttpStatusCode;
import JavaREST.Framework.MimeType;
import JavaREST.Framework.HttpStatusService;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
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
