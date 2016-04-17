package ConnectionServer.Framework;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
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

    //private String pattern;
    private String[] segments;
    private String[] variables;

    private Pattern patternMatcherRegEx;

    private Pattern regExFindSegments = Pattern.compile("(?!(\\{)*[A-Za-z0-9_]*}).?[^{]+");

    private Pattern regExFindVariables = Pattern.compile("(\\{[A-Za-z0-9_]+\\})");

    private Pattern endOfUri = Pattern.compile("(?!\\/)([A-Za-z0-9_&%]+)$", Pattern.CASE_INSENSITIVE);



    public void pattern(String pattern) {

        if(pattern != null) {
            String pat = fixTrailingForwardSlash(pattern.trim());
            segments = getSegments(pat);
            variables = getVariables(pat);
            patternMatcherRegEx = Pattern.compile(generateRegExFromPattern(), Pattern.CASE_INSENSITIVE);
        }



    }

    public boolean isMatch(String o) {

        if(o == null || o.isEmpty()) return false;

        String sut = o.trim();

        return patternMatcherRegEx.matcher(sut).find();


        //return o != null &&
        //        pattern.equalsIgnoreCase(o.trim()) && !o.isEmpty();
    }

    private String generateRegExFromPattern() {
        StringBuilder builder = new StringBuilder();

        int insertedVariables = 0;

        for(String segment : segments) {
            builder.append(Pattern.quote(segment));
            if(variables.length > 0 && insertedVariables < variables.length) {
                builder.append("([A-Za-z0-9_%&]*)");
                insertedVariables++;
            }
        }

        builder.append("(\\/)?");

        builder.append("$");

        return builder.toString();
    }

    public String[] getVariables(String pattern) {
        return getMatches(regExFindVariables, pattern);
    }

    private String[] getSegments(String pattern) {

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

    public String getVariable(String s, String name) {
        int index;
        if(isMatch(s) && (index = variableIndex(name)) >= 0) {
                Matcher matcher = patternMatcherRegEx.matcher(s);
                return matcher.find() ? matcher.group(index + 1) : null;
        }
        return null;
    }

    private int variableIndex(String name) {
        for(int i = 0; i < variables.length; i++) {
            if(variables[i].contentEquals("{" + name + "}"))
                return i;
        }
        return -1;
    }

    public Map<String, String> getVariableValues(String s) {
        if(isMatch(s)) {
            Map<String, String> ret = new LinkedHashMap<>();
            for(String variable : variables) {
                variable = variable.replace("{", "").replace("}", "");
                ret.put(variable, getVariable(s, variable));
            }
            return ret;
        }
        return null;
    }

    public int getVariableCount() {
        return variables.length;
    }
}
