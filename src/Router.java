import Framework.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class Router {
    //List<Framework.Route> routes = new LinkedList<>();
    private Map<String, Route> routes = new LinkedHashMap<>();
    private Map<HttpStatus, Route> defaultRoutes = new LinkedHashMap<>();

    private HttpResponse notFound = (new HttpResponseFactory(new HttpStatusService())).status(HttpStatusCode.NOT_FOUND)
            .contentType(MimeType.html)
            .body(new HttpResponseBody() {
                String builder = "<!DOCTYPE html>\r\n" +
                        "<html>\r\n" +
                        "<head>\r\n" +
                        "<title>404 Not Found</title>\r\n" +
                        "</head>\r\n<body>\r\n" +
                        "<h1>Error 404</h1>\r\n" +
                        "<p>The requested Page could not be found...</p>\r\n" +
                        "</body>\r\n</html>";
                ByteArrayInputStream stream = new ByteArrayInputStream(builder.getBytes());
                long size = builder.getBytes().length;
                @Override
                public InputStream getBodyInputStream() {
                    return stream;
                }
                @Override
                public long getSize() { return size; }
            }).build();

    private HttpResponse serverError = (new HttpResponseFactory(new HttpStatusService())).status(HttpStatusCode.INTERNAL_SERVER_ERROR)
            .contentType(MimeType.html)
            .body(new HttpResponseBody() {
                String builder = "<!DOCTYPE html>\r\n" +
                        "<html>\r\n" +
                        "<head>\r\n" +
                        "<title>500 Internal Server Error</title>\r\n" +
                        "</head>\r\n<body>\r\n" +
                        "<h1>Error 500</h1>\r\n" +
                        "<p>The Server encountered an error. Please contact the system administrator...</p>\r\n" +
                        "</body>\r\n</html>";
                ByteArrayInputStream stream = new ByteArrayInputStream(builder.getBytes());
                long size = builder.getBytes().length;
                @Override
                public InputStream getBodyInputStream() {
                    return stream;
                }
                @Override
                public long getSize() { return size; }
            }).build();

    public String add(Route route) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        routes.put(uuid, route);
        return uuid;
    }

    public void remove(String uuid) {
        routes.remove(uuid);
    }

    public String add(String uriPattern, Route route) {
        return add(route);
    }

    public HttpResponse route(HttpRequest request) {
        for(Route route : routes.values()) {
            if(route.isMatch(request)) {
                try {
                    return route.route(request);
                } catch(RouteNotFoundException e) {
                    return notFound;
                } catch(InternalServerErrorException e) {
                    return serverError;
                }
            }
        }
        return notFound;
    }

    public void setDefaultRoute(HttpStatus status, Route route) {
        defaultRoutes.put(status, route);
    }
}
