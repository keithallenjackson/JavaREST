package Common;

import Common.Framework.LogFormatter;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/5/2016
 * File:
 * Description:
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
