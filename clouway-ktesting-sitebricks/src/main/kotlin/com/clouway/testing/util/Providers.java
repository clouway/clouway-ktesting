package com.clouway.testing.util;

import com.google.inject.Provider;

import java.util.Arrays;
import java.util.Iterator;

/**
 *  Test double provider factory. 
 *
 * @author Miroslav Genov (mgenov@gmail.com)
 */
public class Providers {

  public static <T> Provider<T> of(final T... value) {
    return new Provider<T>() {

      private Iterator<T> values = Arrays.asList(value).iterator();
      private T lastValue = null;

      public T get() {
        
        if (!values.hasNext()) {
          return lastValue;
        }

        lastValue = values.next();
        return lastValue;
      }

    };
  }

}
