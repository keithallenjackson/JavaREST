package ConnectionServer.UnitTests;

import ConnectionServer.Framework.IRequest;
import ConnectionServer.Framework.HttpRequestParseException;
import Common.Framework.Verb;
import ConnectionServer.HttpRequestFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

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
        IRequest sut = factory.verb("get").uri("/").protocol("HTTP").version("1.1").hostname("google.com").build();
        IRequest comp = new IRequest() {
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
        };

        assertThat(sut.getHostname(), is(equalTo(comp.getHostname())));
        assertThat(sut.getUri(), is(equalTo(comp.getUri())));
        assertThat(sut.getVerb(), is(equalTo(comp.getVerb())));

    }

    @Test
    public void separateProtocolAndVersion() throws HttpRequestParseException {
        IRequest sut = factory.protocolAndVersion("HTTP/1.1")
                .build();

        assertThat(sut.getProtocol(), is(equalTo("HTTP")));
        assertThat(sut.getVersion(), is(equalTo("1.1")));
    }

    @Test
    public void seperateHeaderFromHeaderString() throws HttpRequestParseException {
        String headerString = "X-Custom-Header: SomeValue";
        IRequest sut = factory.header(headerString).build();

        assertThat(sut.getHeaders().get("X-Custom-Header")[0], is(equalTo("SomeValue")));


    }

    @Test
    public void setHeaderInformationByGivingKeyValuePairs() throws HttpRequestParseException {
        String key = "X-Custom-Header";
        String[] values = new String[] {"One", "Two", "Three"};

        IRequest sut = factory.header(key, values).build();

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
}
