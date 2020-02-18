package com.clouway.testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public final class CommonMatchers {
  private static final Gson gson = new GsonBuilder().create();

  /**
   * Matcher that is matching the provided value as example one.
   *
   * @param example the example value
   * @param <T> the matching type
   * @return an type safe matcher for matching values
   */
  public static <T> Matcher<T> matching(final T example) {
    return new TypeSafeMatcher<T>() {
      private String valueAsJson;
      private String exampleAsJson;

      @Override
      protected boolean matchesSafely(T value) {
        JsonParser parser = new JsonParser();
        exampleAsJson = gson.toJson(example);
        valueAsJson = gson.toJson(value);

        return parser.parse(exampleAsJson).equals(parser.parse(valueAsJson));
      }

      @Override
      public void describeTo(Description description) {
        description.appendValue(valueAsJson);
      }

      @Override
      protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(exampleAsJson);
      }
    };
  }

  /**
   * Matcher that is matching the provided value as example one using JSON equality.
   *
   * @param example the example value
   * @return an type safe matcher for matching values
   */
  public static Matcher<String> matchingJsonValue(final String example) {
    return new TypeSafeMatcher<String>() {
      @Override
      protected boolean matchesSafely(String value) {
        JsonParser parser = new JsonParser();
        JsonElement sideA = parser.parse(example);
        JsonElement sideB = parser.parse(value);

        return sideA.equals(sideB);
      }

      @Override
      public void describeTo(Description description) {
        description.appendValue(example);
      }

      @Override
      protected void describeMismatchSafely(String value, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(value);
      }
    };
  }

  /**
   * Matcher that is matching if value will be same with example after json transformation.
   *
   * @param example the example value
   * @param <T> the matching type
   * @return an type safe matcher for matching values
   */
  public static <T> Matcher<T> asJsonWillBe(final String example) {
    return new TypeSafeMatcher<T>() {
      private String valueAsJson;
      private String exampleAsJson;

      @Override
      protected boolean matchesSafely(T value) {
        JsonParser parser = new JsonParser();
        exampleAsJson = gson.toJson(value);
        valueAsJson = example;

        return parser.parse(exampleAsJson).equals(parser.parse(valueAsJson));
      }

      @Override
      public void describeTo(Description description) {
        description.appendValue(valueAsJson);
      }

      @Override
      protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(exampleAsJson);
      }
    };
  }
}
