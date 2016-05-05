package JavaREST.Framework;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


@FunctionalInterface
public interface RouteLambda {
    HttpResponse func(HttpRequest request);
}
