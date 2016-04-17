package ConnectionServer;

import Common.HttpResponse;
import Common.HttpResponseBody;
import Common.MimeType;
import Common.Framework.HttpRequest;
import ConnectionServer.Framework.RouteFunction;
import ConnectionServer.Wrappers.HttpStatusService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/12/2016
 * File:
 * Description:
 */
public class Router {
    //List<Route> routes = new LinkedList<>();
    private Map<String, Route> routes = new LinkedHashMap<>();
    private Map<HttpStatus, Route> defaultRoutes = new LinkedHashMap<>();

    public String add(Route route) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        routes.put(uuid, route);
        return uuid;
    }

    public void remove(String uuid) {
        routes.remove(uuid);
    }

    public String add(String uriPattern, RouteFunction func) {
        return add(new Route(uriPattern, func));
    }

    public HttpResponse route(HttpRequest request) {
        for(Route route : routes.values()) {
            if(route.isMatch(request)) {
                return route.route(request);
            }
        }

        // Generate a 404 not found!
        return (new HttpResponseFactory(new HttpStatusService())).status(HttpStatusCode.NOT_FOUND)
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
    }

    public void setDefaultRoute(HttpStatus status, Route route) {
        defaultRoutes.put(status, route);
    }
}
