package com.clouway.testing;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgi Georgiev (georgi.georgiev@clouway.com)
 */
public class WarnConstructorFactory implements TypeAdapterFactory {
  private List<Class<?>> wrappers = new ArrayList<Class<?>>() {{
    add(Boolean.class);
    add(Character.class);
    add(String.class);
    add(Byte.class);
    add(Short.class);
    add(Integer.class);
    add(Long.class);
    add(Float.class);
    add(Double.class);
  }};

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    Class<? super T> rawType = type.getRawType();

    if (!(wrappers.contains(rawType) || rawType.isInterface() || rawType.isPrimitive() || rawType.isEnum())) {
      Constructor<?>[] constructors = rawType.getConstructors();
      boolean hasNoArgsConstructor = false;
      for (Constructor<?> constructor : constructors) {
        if (constructor.getParameterTypes().length == 0) {
          hasNoArgsConstructor = true;
        }
      }

      if (!hasNoArgsConstructor) {
        throw new IllegalStateException("'" + type + "' has no public no-args constructor");
      }
    }
    return null;
  }
}
