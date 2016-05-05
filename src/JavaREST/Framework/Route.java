package JavaREST.Framework;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */

public abstract class Route {
    protected UriStringParser parser;

    protected Route(String uriPattern) {
        this.parser = new UriStringParser();
        parser.pattern(uriPattern);
    }

    public abstract boolean isMatch(HttpRequest uri);

    public abstract HttpResponse route(HttpRequest request) throws InternalServerErrorException, RouteNotFoundException;

    @Override
    public abstract boolean equals(Object o);
}
