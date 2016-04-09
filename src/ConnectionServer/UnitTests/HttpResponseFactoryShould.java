package ConnectionServer.UnitTests;

import Common.MimeType;
import ConnectionServer.HttpResponseFactory;
import ConnectionServer.HttpStatusCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/5/2016
 * File:
 * Description:
 */
public class HttpResponseFactoryShould {

    private HttpResponseFactory factory;

    @Before
    public void setUp() {
        factory = new HttpResponseFactory();

    }

    @After
    public void cleanUp() {

    }

    @Test
    public void returnAnOKResponseToRoot() {
        factory.protocol("http").version("1.1").status(HttpStatusCode.OK).contentType(MimeType.text).body(null);
    }

}
