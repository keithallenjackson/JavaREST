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
    public void findOneVariableGivenASingleVariableString() {
        parser.pattern(oneVariableString);
        String[] variables = parser.getVariables();

        assertThat(variables.length, is(1));
        assertThat(variables[0], is(equalTo("{version}")));
    }

    @Test
    public void findTwoSegmentsInSingleVariableString() {
        parser.pattern(oneVariableString);
        String[] segments = parser.getSegments();

        assertThat(segments.length, is(2));
        assertThat(segments[0], is(equalTo("/api/")));
        assertThat(segments[1], is(equalTo("/todo")));
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

}
