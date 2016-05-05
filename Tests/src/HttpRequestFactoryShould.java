import JavaREST.Framework.*;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */

public class HttpRequestFactoryShould {

    private HttpRequestFactory factory;

    @Before
    public void setUp() {
        factory = new HttpRequestFactory()
                .verb("get")
                .uri("/")
                .protocolAndVersion("HTTP/1.1")
                .hostname("google.com");
    }

    @Test
    public void generateAValidIResponse() throws HttpRequestParseException{
        HttpRequest sut = factory.verb("get").uri("/").protocol("HTTP").version("1.1").hostname("google.com").build();
        HttpRequest comp = new HttpRequest() {
            @Override
            public Verb getVerb() {
                return Verb.GET;
            }

            @Override
            public URI getUri() {
                return URI.create("/");
            }

            @Override
            public String getHostname() {
                return "google.com";
            }

            @Override
            public String getProtocol() {
                return "HTTP";
            }

            @Override
            public String getVersion() {
                return "1.1";
            }

            @Override
            public Map<String, String[]> getHeaders() {
                return null;
            }

            @Override
            public MimeType getContentType() {
                return null;
            }

            @Override
            public InputStream getBody() {
                return null;
            }
        };

        assertThat(sut.getHostname(), is(equalTo(comp.getHostname())));
        assertThat(sut.getUri(), is(equalTo(comp.getUri())));
        assertThat(sut.getVerb(), is(equalTo(comp.getVerb())));

    }

    @Test
    public void separateProtocolAndVersion() throws HttpRequestParseException {
        HttpRequest sut = factory.protocolAndVersion("HTTP/1.1")
                .build();

        assertThat(sut.getProtocol(), is(equalTo("HTTP")));
        assertThat(sut.getVersion(), is(equalTo("1.1")));
    }

    @Test
    public void seperateHeaderFromHeaderString() throws HttpRequestParseException {
        String headerString = "X-Custom-Header: SomeValue";
        HttpRequest sut = factory.header(headerString).build();

        assertThat(sut.getHeaders().get("X-Custom-Header")[0], is(equalTo("SomeValue")));


    }

    @Test
    public void setHeaderInformationByGivingKeyValuePairs() throws HttpRequestParseException {
        String key = "X-Custom-Header";
        String[] values = new String[] {"One", "Two", "Three"};

        HttpRequest sut = factory.header(key, values).build();

        assertThat(sut.getHeaders().get(key), is(equalTo(values)));
    }

    @Test
    public void toStringShouldBeHttpRequestMessage() throws HttpRequestParseException {
        String comp = "GET / HTTP/1.1\r\nHostname: google.com\r\n\r\n";

        assertThat(factory.build().toString(), is(equalTo(comp)));
    }

    @Test
    public void toStringShouldBeHttpRequestMessageWithHeaders() throws HttpRequestParseException {
        String comp = "GET / HTTP/1.1\r\nHostname: google.com\r\nX-Custom-Header: item1, item2, item3\r\n\r\n";

        factory.header("X-Custom-Header", new String[] {"item1", "item2", "item3"});

        assertThat(factory.build().toString(), is(equalTo(comp)));
    }

    @Test
    public void toStringShouldBeHttpRequestMessageWithHeadersWithOnlyOneValue() throws HttpRequestParseException {
        String comp = "GET / HTTP/1.1\r\nHostname: google.com\r\nX-Custom-Header: item1\r\n\r\n";

        factory.header("X-Custom-Header", new String[] {"item1"});

        assertThat(factory.build().toString(), is(equalTo(comp)));
    }

    @Test(expected = HttpRequestParseException.class)
    public void throwExceptionWhenBuildIsCalledWithHostnameNull() throws HttpRequestParseException {
        factory.hostname(null).build();
    }

    @Test(expected = HttpRequestParseException.class)
    public void throwExceptionWhenBuildIsCalledWithUriNull() throws HttpRequestParseException {
        factory.uri(null).build();
    }

    @Test(expected = HttpRequestParseException.class)
    public void throwExceptionWhenBuildIsCalledWithInvalidUri() throws HttpRequestParseException {
        factory.uri("@#fSDF23wfsfda@#4").build();
    }

    @Test
    public void generateIRequsetCapableOfEqualityComparison() throws HttpRequestParseException {
        HttpRequest req1 = factory.protocol("http").hostname("google.com").uri("/").version("1.1").verb("get").build();
        HttpRequest req2 = factory.build();

        assertThat(req1, is(equalTo(req2)));
        assertThat(req1.equals(req2), is(equalTo(true)));
    }

    @Test
    public void generateADifferentIRequestEveryTimeBuildIsCalled() throws HttpRequestParseException {
        HttpRequest req1 = factory.protocol("http").hostname("google.com").uri("/").version("1.1").verb("get").build();
        HttpRequest req2 = factory.build();

        assertThat(req2.hashCode(), is(not(equalTo(req1.hashCode()))));
    }
}
