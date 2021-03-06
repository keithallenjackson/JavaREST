import JavaREST.Framework.HttpResponse;
import JavaREST.Framework.MimeType;
import JavaREST.Framework.HttpResponseFactory;
import JavaREST.Framework.HttpStatusCode;
import JavaREST.Framework.HttpStatusService;
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
 * Date: 4/18/2016
 * License: MIT
 *
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
