package ConnectionServer.UnitTests;

import ConnectionServer.Framework.UriStringParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

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
public class UriStringParserShould {

    private UriStringParser parser;
    private String nullString = null, emptyString = "", oneVariableString = "/api/{version}/todo",
            staticString = "/api/456/todo";


    @Before
    public void setup() {
        parser = new UriStringParser();
    }

    @After
    public void cleanup() {

    }

    @Test
    public void acceptEmptyOrNullForPatternAndNotMatchAnything() {
        parser.pattern(nullString);

        assertThat(parser.isMatch(null), is(equalTo(false)));

        parser.pattern(emptyString);

        assertThat(parser.isMatch(""), is(equalTo(false)));
    }

    @Test
    public void matchStaticStringsThatAreIdentical() {
        parser.pattern(staticString);
        assertThat(parser.isMatch(staticString), is(equalTo(true)));
    }

    @Test
    public void matchStaticStringThatAreIdenticalExceptCase() {
        parser.pattern(staticString);
        assertThat(parser.isMatch(staticString.toUpperCase()), is(equalTo(true)));
    }

    @Test
    public void matchStaticStringThatAreIdenticalExceptLeadingAndTrailingWhitespace() {
        parser.pattern(" " + staticString + " ");
        assertThat(parser.isMatch(" " + staticString + "  "), is(equalTo(true)));
    }

    @Test
    public void matchDynamicStringWithVariableInserted() {
        parser.pattern(oneVariableString);
        assertThat(parser.isMatch("/api/3/todo"), is(true));
    }

    @Test
    public void matchDynamicStringWithTwoConsecutiveVariables() {
        parser.pattern("/Index/{Big_Number_1}/{Big_Number_2}");
        assertThat(parser.isMatch("/index/88994040/329394929"), is(true));
    }

    @Test
    public void matchDynamicStringWithTwoConsecutiveVariablesWithTrailingSlash() {
        parser.pattern("/Index/{Big_Number_1}/{Big_Number_2}/");
        assertThat(parser.isMatch("/index/123151/23412521352/"), is(true));
    }

    @Test
    public void matchShouldBeForgivingWithTrailingSlash() {
        parser.pattern("/Index/{Big_Number}/{Big_Number2}/");
        assertThat(parser.isMatch("/index/235124346/23426243"), is(true));
    }

    @Test
    public void notBeForgivingOfATrailingSlashIfTwoAreSpecifiedAtEndOfString() {
        parser.pattern("/Index/{Big1}/{Big4}//");
        assertThat(parser.isMatch("/index/2367884/383653235"), is(false));
    }

    @Test
    public void notBeForgivingOfATrailingSlashIfTwoAreSpecifiedAtEndOfStringWithCorrectEnding() {
        parser.pattern("/Index/{Big1}/{Big4}//");
        assertThat(parser.isMatch("/index/32523325/34627243534/"), is(true));
    }

    @Test
    public void pullVariableValueBasedOnPattern() {
        parser.pattern(oneVariableString);
        assertThat(parser.getVariable("/api/223/todo", "version"), is(equalTo("223")));
    }

    @Test
    public void pullTwoVariableValuesBasedOnPattern() {
        parser.pattern("/Index/{Big1}/{Big4}//");
        String uri = "/index/556421/bigString/";
        assertThat(parser.getVariable(uri, "Big1"), is(equalTo("556421")));
        assertThat(parser.getVariable(uri, "Big4"), is(equalTo("bigString")));
    }

    @Test
    public void pullTowVariableValuesBaseOnPatternWithForgivingForwardSlash() {
        parser.pattern("/Index/{Big123}/{Big78}/");
        String uri = "/index/99333/2340";

        assertThat(parser.getVariable(uri, "Big123"), is(equalTo("99333")));
        assertThat(parser.getVariable(uri, "Big78"), is(equalTo("2340")));

    }

    @Test
    public void notCareAboutAMissingForwardSlashAtTheBeginning() {
        parser.pattern("Index/{Var1}/bigbreakfast");
        assertThat(parser.isMatch("/index/123/bigbreakfast"), is(true));
        assertThat(parser.getVariable("/index/123/bigbreakfast", "Var1"), is(equalTo("123")));
    }

    @Test
    public void notMatchAUriLongerThanThePattern() {
        parser.pattern("//");
        assertThat(parser.isMatch("/api/test1"), is(false));
    }
}
