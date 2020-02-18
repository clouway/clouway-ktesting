package com.clouway.testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Georgi Georgiev (georgi.georgiev@clouway.com)
 */
public class TestingGsonFactory {
  public static Gson create() {
    return new GsonBuilder().registerTypeAdapterFactory(new WarnConstructorFactory()).create();
  }
}


