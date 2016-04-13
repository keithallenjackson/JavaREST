package ConnectionServer.Framework;

import Common.HttpResponse;

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
public interface RouteFunction {
    HttpResponse func(HttpRequest request);
}
