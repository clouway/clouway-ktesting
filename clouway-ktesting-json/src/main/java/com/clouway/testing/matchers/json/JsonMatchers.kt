package com.clouway.testing.matchers.json

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * @author Stanimir Iliev <stanimir.iliev@clouway.com />
 */

object JsonMatchers {
  fun isSameJsonAs(example: String): Matcher<String> {
    return object : TypeSafeMatcher<String>() {
      var value = ""
      val parser = JsonParser()
      val gson = GsonBuilder().setPrettyPrinting().create()

      override fun matchesSafely(value: String): Boolean {
        this.value = value

        return parser.parse(example) == parser.parse(value)
      }

      override fun describeTo(description: Description) {
        description.appendValue(gson.toJson((parser.parse(example))))
      }

      override fun describeMismatchSafely(item: String, mismatchDescription: Description) {
        mismatchDescription.appendText("was ").appendValue(gson.toJson((parser.parse(item))))
      }
    }
  }
}