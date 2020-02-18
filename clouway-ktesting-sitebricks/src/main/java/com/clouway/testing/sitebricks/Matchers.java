package com.clouway.testing.sitebricks;

import com.clouway.telcong.testing.JsonBuilder;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Key;
import com.google.sitebricks.client.Transport;
import com.google.sitebricks.client.transport.Text;
import com.google.sitebricks.headless.Reply;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;

import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.util.Objects;

import static com.clouway.telcong.testing.JsonBuilder.aNewJson;
import static com.clouway.telcong.testing.JsonBuilder.aNewJsonArray;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public final class Matchers {

  /**
   * Cookie matcher that verifies that name, value and duration are matching for the cookie.
   *
   * @param name              the name of the cookie
   * @param value             the cookie value
   * @param durationInSeconds the amount of time which cookie will be available
   * @return a matcher that matches cookie for it's name, value and duration
   */
  public static Matcher<Cookie> cookie(final String name, final String value, final int durationInSeconds) {

    return new TypeSafeMatcher<Cookie>() {
      @Override
      protected boolean matchesSafely(Cookie item) {
        return Objects.equals(item.getName(), name) && Objects.equals(item.getValue(), value) && item.getMaxAge() == durationInSeconds;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(String.format("Cookie(%s: %s)", name, value));
      }

      @Override
      protected void describeMismatchSafely(Cookie item, Description mismatchDescription) {
        mismatchDescription.appendText(String.format("was Cookie (%s: %s)", item.getName(), item.getValue()));
      }
    };
  }

  public static Matcher<Reply<?>> containsValue(final Object value) {
    return new TypeSafeMatcher<Reply<?>>() {
      @Override
      public boolean matchesSafely(Reply<?> reply) {
        Object responseValue = property("entity", reply);
        return value.equals(responseValue);
      }

      @Override
      protected void describeMismatchSafely(Reply<?> reply, Description mismatchDescription) {
        mismatchDescription.appendText("was ");
        mismatchDescription.appendValue(property("entity", reply));
      }

      public void describeTo(Description description) {
        description.appendText("Reply value to be ");
        description.appendValue(value);
      }
    };
  }

  public static Matcher<Reply<?>> containsJson(final String expectedJsonValue) {
    return new TypeSafeMatcher<Reply<?>>() {
      @Override
      protected boolean matchesSafely(Reply<?> item) {
        Key<? extends Transport> key = property("transport", item);
        // Default transport is Text which means that JSON transport
        // wasn't specified and error should be returned.
        if (Key.get(Text.class).equals(key)) {
          return false;
        }
        String jsonContent = asJsonContent(item, key);

        JsonParser parser = new JsonParser();
        JsonElement actual = parser.parse(jsonContent);
        JsonElement expected = parser.parse(expectedJsonValue);
        return actual.equals(expected);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(toPrettyFormat(expectedJsonValue));
      }

      @Override
      protected void describeMismatchSafely(Reply<?> item, Description mismatchDescription) {
        Key<? extends Transport> key = property("transport", item);
        // Default transport is Text which means that JSON transport
        // wasn't specified and error should be returned.
        if (Key.get(Text.class).equals(key)) {
          mismatchDescription.appendText("Text Transport was specified.");
          return;
        }

        String jsonContent = asJsonContent(item, key);
        mismatchDescription.appendText("was\n");
        mismatchDescription.appendText(toPrettyFormat(jsonContent));
      }

      private String asJsonContent(Reply<?> reply, Key<? extends Transport> key) {

        try {
          Transport transport = (Transport) key.getTypeLiteral().getRawType().newInstance();
          Object value = property("entity", reply);
          OutputStream bout = new ByteArrayOutputStream();
          transport.out(bout, Object.class, value);

          return bout.toString();

        } catch (IllegalAccessException | InstantiationException | IOException e) {
          return Throwables.getStackTraceAsString(e);
        }
      }
    };
  }


  public static Matcher<Reply<?>> containsJson(final JsonBuilder content) {
    return new TypeSafeMatcher<Reply<?>>() {
      @Override
      protected boolean matchesSafely(Reply<?> item) {
        Key<? extends Transport> key = property("transport", item);
        // Default transport is Text which means that JSON transport
        // wasn't specified and error should be returned.
        if (Key.get(Text.class).equals(key)) {
          return false;
        }
        String jsonContent = asJsonContent(item, key);
        return jsonContent.equals(content.build());
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(toPrettyFormat(content.build()));
      }

      @Override
      protected void describeMismatchSafely(Reply<?> item, Description mismatchDescription) {
        Key<? extends Transport> key = property("transport", item);
        // Default transport is Text which means that JSON transport
        // wasn't specified and error should be returned.
        if (Key.get(Text.class).equals(key)) {
          mismatchDescription.appendText("Text Transport was specified.");
          return;
        }

        String jsonContent = asJsonContent(item, key);
        mismatchDescription.appendText("was ");
        mismatchDescription.appendText(toPrettyFormat(jsonContent));
      }

      private String asJsonContent(Reply<?> reply, Key<? extends Transport> key) {

        try {
          Transport transport = (Transport) key.getTypeLiteral().getRawType().newInstance();
          Object value = property("entity", reply);
          OutputStream bout = new ByteArrayOutputStream();
          transport.out(bout, Object.class, value);
          return bout.toString();

        } catch (IllegalAccessException | InstantiationException | IOException e) {
          return Throwables.getStackTraceAsString(e);
        }
      }
    };
  }

  public static Matcher<Reply<?>> containsPrettyJson(final JsonBuilder content) {
    return new TypeSafeMatcher<Reply<?>>() {
      @Override
      protected boolean matchesSafely(Reply<?> item) {
        Key<? extends Transport> key = property("transport", item);
        // Default transport is Text which means that JSON transport
        // wasn't specified and error should be returned.
        if (Key.get(Text.class).equals(key)) {
          return false;
        }
        String jsonContent = asJsonContent(item, key);
        return jsonContent.equals(content.buildPrettyJson());
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(toPrettyFormat(content.buildPrettyJson()));
      }

      @Override
      protected void describeMismatchSafely(Reply<?> item, Description mismatchDescription) {
        Key<? extends Transport> key = property("transport", item);
        // Default transport is Text which means that JSON transport
        // wasn't specified and error should be returned.
        if (Key.get(Text.class).equals(key)) {
          mismatchDescription.appendText("Text Transport was specified.");
          return;
        }

        String jsonContent = asJsonContent(item, key);
        mismatchDescription.appendText("was ");
        mismatchDescription.appendText(toPrettyFormat(jsonContent));
      }

      private String asJsonContent(Reply<?> reply, Key<? extends Transport> key) {

        try {
          Transport transport = (Transport) key.getTypeLiteral().getRawType().newInstance();
          Object value = property("entity", reply);
          OutputStream bout = new ByteArrayOutputStream();
          transport.out(bout, Object.class, value);
          return bout.toString();

        } catch (IllegalAccessException | InstantiationException | IOException e) {
          return Throwables.getStackTraceAsString(e);
        }
      }
    };
  }

  public static Matcher<Reply<?>> containsErrorJson(final String code, final String message) {
    return containsJson(aNewJson()
                .add("message", message)
                .add("errors", aNewJsonArray(
                        aNewJson().add("code", code)
                                .add("message", message)
                                .add("field", "")
                                .add("resource", ""))));
  }

  public static Matcher<Reply<?>> containsError(final String message) {
    return containsJson(aNewJson().add("message", message));
  }

  public static Matcher<Reply<?>> isOk() {
    return returnCodeMatcher(HttpURLConnection.HTTP_OK);
  }

  public static Matcher<Reply<?>> isCreated() {
    return returnCodeMatcher(HttpURLConnection.HTTP_CREATED);
  }

  public static Matcher<Reply<?>> isBadRequest() {
    return returnCodeMatcher(HttpURLConnection.HTTP_BAD_REQUEST);
  }

  public static Matcher<Reply<?>> isNotFound() {
    return returnCodeMatcher(HttpURLConnection.HTTP_NOT_FOUND);
  }

  public static Matcher<Reply<?>> isUnauthorized() {
    return returnCodeMatcher(HttpURLConnection.HTTP_UNAUTHORIZED);
  }

  public static Matcher<Reply<?>> isForbidden() {
    return returnCodeMatcher(HttpURLConnection.HTTP_FORBIDDEN);
  }

  public static Matcher<Reply<?>> isConflict() {
    return returnCodeMatcher(HttpURLConnection.HTTP_CONFLICT);
  }

  public static Matcher<Reply<?>> isUnprocessableEntity() {
    return returnCodeMatcher(422);
  }

  public static Matcher<Reply<?>> isInternalError() {
    return returnCodeMatcher(HttpURLConnection.HTTP_INTERNAL_ERROR);
  }

  public static Matcher<Reply<?>> isMovedTemp() {
    return returnCodeMatcher(HttpURLConnection.HTTP_MOVED_TEMP);
  }

  private static Matcher<Reply<?>> returnCodeMatcher(final int expectedCode) {
    return new TypeSafeMatcher<Reply<?>>() {
      @Override
      public boolean matchesSafely(Reply<?> reply) {
        Integer status = property("status", reply);
        return Integer.valueOf(expectedCode).equals(status);
      }

      public void describeTo(Description description) {
        description.appendText("status ");
        description.appendValue(expectedCode);
      }

      @Override
      protected void describeMismatchSafely(Reply<?> reply, Description description) {
        Integer status = property("status", reply);
        description.appendText("was ");
        description.appendValue(status);
      }
    };
  }

  @SuppressWarnings("unchecked")
  protected static <T> T property(String fieldName, Reply<?> reply) {
    Field field = null;
    try {
      field = reply.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return (T) field.get(reply);
    } catch (NoSuchFieldException e) {
      Assert.fail(e.getMessage());
    } catch (IllegalAccessException e) {
      Assert.fail(e.getMessage());
    } finally {
      if (field != null) {
        field.setAccessible(false);
      }
    }
    return null;
  }

  private static String toPrettyFormat(String jsonString) {
    JsonParser parser = new JsonParser();
    JsonElement json = parser.parse(jsonString);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(json);
  }

}
