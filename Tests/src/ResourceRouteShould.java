import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.Is.*;

import JavaREST.Framework.*;
import JavaREST.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */

public class ResourceRouteShould {

    public class FakeResource {
        String s1;
        int int2;
        boolean wasCalled = false;

        @HttpVerb(value = Verb.GET)
        public HttpResponse getMethod(String s1, int int2) {
            this.s1 = s1;
            this.int2 = int2;
            wasCalled = true;
            return null;
        }
    }

    public class FakeResourceInjection {
        String s1;
        int int2;
        HttpRequest request;
        boolean wasCalled = false;

        @HttpVerb(value = Verb.GET)
        public HttpResponse getMethod(HttpRequest request, String s1, int int2) {
            this.request = request;
            this.s1 = s1;
            this.int2 = int2;
            wasCalled = true;
            return null;
        }
    }

    String uri;
    @Before
    public void setup() {
        uri = "/api/v1/{s1}/{int2}";
    }

    @After
    public void cleanup() {

    }

    @Test
    public void invokeCorrectMethodThroughReflectionByHttpVerb() throws HttpRequestParseException, InternalServerErrorException, RouteNotFoundException {
        FakeResource fake = new FakeResource();
        ResourceRoute resourceRoute = new ResourceRoute(uri, fake);

        HttpResponse response = resourceRoute.route(new HttpRequestFactory().protocolAndVersion("HTTP/1.1")
        .uri("/api/v1/HelloThere/123").verb(Verb.GET).hostname("localhost").build());

        assertThat(fake.wasCalled, is(true));
        assertThat(fake.s1, is(equalTo("HelloThere")));
        assertThat(fake.int2, is(123));

    }

    @Test
    public void invokeCorrectMethodAndInjectRequest() throws HttpRequestParseException, InternalServerErrorException, RouteNotFoundException {
        FakeResourceInjection fake = new FakeResourceInjection();
        ResourceRoute resourceRoute = new ResourceRoute(uri, fake);

        HttpRequest requestSent = new HttpRequestFactory().protocolAndVersion("HTTP/1.1")
                .uri("/api/v1/HelloThere/123").verb(Verb.GET).hostname("localhost").build();
        HttpResponse response = resourceRoute.route(requestSent);

        assertThat(fake.wasCalled, is(true));
        assertThat(fake.request, is(requestSent));
        assertThat(fake.s1, is(equalTo("HelloThere")));
        assertThat(fake.int2, is(123));

    }

}
