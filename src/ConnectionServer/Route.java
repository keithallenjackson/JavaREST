package ConnectionServer;

import Common.HttpResponse;
import Common.Framework.HttpRequest;
import ConnectionServer.Framework.RouteFunction;
import ConnectionServer.Framework.UriStringParser;

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
public class Route {
    private UriStringParser parser;
    private RouteFunction handler;

    public Route(String uriPattern, RouteFunction handler) {
        parser = new UriStringParser();
        parser.pattern(uriPattern);
        this.handler = handler;
    }

    public boolean isMatch(HttpRequest request) {
        return parser.isMatch(request.getUri().getRawPath());
    }

    public HttpResponse route(HttpRequest request) {
        return handler.func(request);
    }

    @Override
    public boolean equals(Object o) {
        try {
            return isMatch((HttpRequest)o);
        } catch(ClassCastException e) {
            return false;
        }
    }

}
