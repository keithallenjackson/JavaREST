import Framework.HttpResponse;
import Framework.MimeType;
import Framework.HttpResponseFactory;
import Framework.HttpStatusCode;
import Framework.HttpStatusService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

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
        factory = new HttpResponseFactory(new HttpStatusService());

    }

    @After
    public void cleanUp() {

    }

    @Test
    public void returnAnOKResponseToRoot() throws InvalidArgumentException {
        HttpResponse response = factory.protocol("http").version("1.1").status(HttpStatusCode.OK)
                .contentType(MimeType.text).body(null).build();

        assertThat(response.toString(), is(not(equalTo(null))));

    }

}
