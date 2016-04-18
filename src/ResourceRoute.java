import Framework.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class ResourceRoute extends Route{
    private Object handler;

    public ResourceRoute(String uriPattern, Object handler) {
        super(uriPattern);
        this.handler = handler;
    }

    @Override
    public boolean isMatch(HttpRequest request) {
        return parser.isMatch(request.getUri().getRawPath());
    }

    @Override
    public HttpResponse route(HttpRequest request) throws InternalServerErrorException, RouteNotFoundException {
        Method method = getMethodWithAnnotatedVerb(request.getVerb(), handler);

        if(method == null) throw new RouteNotFoundException();

        int methodParameterCount = method.getParameterCount();
        Parameter[] parameters = method.getParameters();
        boolean injectRequest = methodParameterCount > 0 && parameters[0].getType().isAssignableFrom(HttpRequest.class);

        if(injectRequest) methodParameterCount--;

        if(methodParameterCount != parser.getVariableCount() ||
                !method.getReturnType().isAssignableFrom(HttpResponse.class))
            throw new InternalServerErrorException();

        Map<String, String> args = parser.getVariableValues(request.getUri().getRawPath());
        Map<Integer, String> order = new LinkedHashMap<>();
        Map<String, Class<?>> argTypes = new LinkedHashMap<>();

        int index = injectRequest ? 1 : 0;

        for(String variable : args.keySet()) {
            order.put(index, variable);
            argTypes.put(variable, parameters[index].getType());
            index++;
        }

        Object[] realArgs = new Object[parameters.length];
        for(int i = 0; i < realArgs.length; i++) {
            String varName = order.get(i);

            if(i == 0 && injectRequest) {
                realArgs[i] = request;
            } else {
                realArgs[i] = toObject(argTypes.get(varName), args.get(varName));
            }
        }

        try {
            return (HttpResponse)method.invoke(handler, realArgs);
        } catch(IllegalAccessException|InvocationTargetException e) {
            throw new InternalServerErrorException();
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

    private static Method getMethodWithAnnotatedVerb(Verb verb, Object o) {
        Class<?> klass = o.getClass();
        while(klass != Object.class) {
            for(Method method : klass.getDeclaredMethods()) {
                if(method.isAnnotationPresent(HttpVerb.class) && method.getAnnotation(HttpVerb.class).value() == verb) {
                    return method;
                }
            }
            klass = klass.getSuperclass();
        }
        return null;
    }

    private static Object toObject( Class clazz, String value ) {
        if( Boolean.class == clazz || boolean.class == clazz ) return Boolean.parseBoolean( value );
        if( Byte.class == clazz || byte.class == clazz) return Byte.parseByte( value );
        if( Short.class == clazz || short.class == clazz) return Short.parseShort( value );
        if( Integer.class == clazz || int.class == clazz) return Integer.parseInt( value );
        if( Long.class == clazz || long.class == clazz) return Long.parseLong( value );
        if( Float.class == clazz || float.class == clazz) return Float.parseFloat( value );
        if( Double.class == clazz || double.class == clazz) return Double.parseDouble( value );
        return value;
    }

}
