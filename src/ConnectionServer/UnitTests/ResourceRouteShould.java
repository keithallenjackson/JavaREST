package ConnectionServer.UnitTests;

import Common.Framework.HttpRequestParseException;
import Common.Framework.Verb;
import Common.HttpRequestFactory;
import Common.HttpResponse;
import Common.HttpResponseBody;
import Common.MimeType;
import ConnectionServer.Framework.Annotations.HttpVerb;
import ConnectionServer.Framework.RouteNotFoundException;
import ConnectionServer.Framework.RoutingReflectionException;
import ConnectionServer.Framework.UriStringParser;
import ConnectionServer.HttpResponseFactory;
import ConnectionServer.HttpStatusCode;
import ConnectionServer.ResourceRoute;
import ConnectionServer.Wrappers.HttpStatusService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/16/2016
 * File:
 * Description:
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

    ResourceRoute resourceRoute;
    FakeResource fake;
    @Before
    public void setup() {
        fake = new FakeResource();
        resourceRoute = new ResourceRoute("/api/v1/{s1}/{int2}", fake);
    }

    @After
    public void cleanup() {

    }

    @Test
    public void invokeCorrectMethodThroughReflectionByHttpVerb() throws HttpRequestParseException, RoutingReflectionException, RouteNotFoundException {
        HttpResponse response = resourceRoute.route(new HttpRequestFactory().protocolAndVersion("HTTP/1.1")
        .uri("/api/v1/HelloThere/123").verb(Verb.GET).hostname("localhost").build());

        assertThat(fake.wasCalled, is(true));
        assertThat(fake.s1, is(equalTo("HelloThere")));
        assertThat(fake.int2, is(123));

    }

}
