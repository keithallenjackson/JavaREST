package JavaREST.Framework;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class TabLogFormatter implements LogFormatter {
    @Override
    public String Format(Object... items) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            builder.append(items);
            builder.append("\t");
        }
        return builder.toString().substring(0, builder.toString().length() - 1);
    }
}
