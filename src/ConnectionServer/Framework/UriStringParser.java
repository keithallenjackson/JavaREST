package ConnectionServer.Framework;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/10/2016
 * File:
 * Description:
 */
public class UriStringParser {

    private String pattern;

    private Pattern regExFindSegments = Pattern.compile("(?!(\\{)*[A-Za-z0-9_]*}).?[^{]+");

    private Pattern regExFindVariables = Pattern.compile("(\\{[A-Za-z0-9_]+\\})");



    public void pattern(String pattern) {

        this.pattern = pattern != null ? pattern.trim() : null;
        this.pattern = fixTrailingForwardSlash(this.pattern);

    }

    public boolean isMatch(String o) {

        if(o == null || o.isEmpty()) return false;

        String sut = o.trim();

        StringBuilder builder = new StringBuilder();

        String[] segments = getSegments();
        String[] variables = getVariables();

        for(String segment : segments) {
            builder.append(Pattern.quote(segment));
            if(variables.length > 0) {
                builder.append("([A-Za-z0-9_%&]*)");
            }
        }

        builder.append("(\\/)?");

        builder.append("$");

        String generatedRegEx = builder.toString();

        Pattern regEx = Pattern.compile(generatedRegEx, Pattern.CASE_INSENSITIVE);

        return regEx.matcher(sut).find();


        //return o != null &&
        //        pattern.equalsIgnoreCase(o.trim()) && !o.isEmpty();
    }

    public String[] getVariables() {
        return getMatches(regExFindVariables, pattern);
    }

    public String[] getSegments() {

        return getMatches(regExFindSegments, pattern);
    }

    private static String[] getMatches(Pattern regex, String pattern) {
        Matcher matcher = regex.matcher(pattern);
        List<String> findings = new LinkedList<>();
        while(matcher.find()) {
            findings.add(matcher.group());
        }

        return findings.toArray(new String[0]);
    }

    private boolean lastCharacterIsForwardSlash(String o) {
        return o != null && !o.isEmpty() && o.substring(o.length() - 1, o.length()).contentEquals("/");
    }

    private String fixTrailingForwardSlash(String o) {
        return lastCharacterIsForwardSlash(o) ? o.substring(0, o.length() - 1) : o;
    }
}
