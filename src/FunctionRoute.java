import Framework.*;

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
public class FunctionRoute extends Route {
    private RouteLambda handler;

    public FunctionRoute(String uriPattern, RouteLambda handler) {
        super(uriPattern);
        this.handler = handler;
    }

    public boolean isMatch(HttpRequest request) {
        return parser.isMatch(request.getUri().getRawPath());
    }

    public HttpResponse route(HttpRequest request) throws RouteNotFoundException {
        if(isMatch(request)) {
            return handler.func(request);
        } else {
            throw new RouteNotFoundException();
        }
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
