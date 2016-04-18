package Framework;

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

@FunctionalInterface
public interface RouteLambda {
    HttpResponse func(HttpRequest request);
}
