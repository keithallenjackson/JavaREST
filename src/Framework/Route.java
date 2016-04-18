package Framework;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/17/2016
 * File:
 * Description:
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
