package com.clouway.testing.matchers.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public class JsonBuilder {
  private static final Gson GSON = new GsonBuilder().create();

  public static JsonBuilder aNewJson() {
    return new JsonBuilder(new JsonObject());
  }

  public static JsonBuilder aNewJsonArray() {
    return new JsonBuilder(new JsonArray());
  }

  public static JsonBuilder aNewJsonArray(JsonBuilder... builders) {
    JsonArray target = new JsonArray();
    for (JsonBuilder each : builders) {
      target.getAsJsonArray().add(each.target);
    }
    return new JsonBuilder(target);
  }

  public static JsonBuilder aNewJsonArray(Integer... values) {
    JsonArray target = new JsonArray();
    for (Integer each : values) {
      target.getAsJsonArray().add(each);
    }
    return new JsonBuilder(target);
  }

  public static JsonBuilder aNewJsonArray(Double... values) {
    JsonArray target = new JsonArray();
    for (Double each : values) {
      target.getAsJsonArray().add(each);
    }
    return new JsonBuilder(target);
  }

  public static JsonBuilder aNewJsonArray(String... values){
    JsonArray target = new JsonArray();
    for (String each: values){
      target.getAsJsonArray().add(each);
    }
    return new JsonBuilder(target);
  }

  public static JsonBuilder aNewJsonArray(Boolean... values){
    JsonArray target = new JsonArray();
    for (Boolean each: values){
      target.getAsJsonArray().add(each);
    }
    return new JsonBuilder(target);
  }

  private JsonElement target;

  public JsonBuilder(JsonElement target) {
    this.target = target;
  }

  public JsonBuilder add(String property, String value) {
    target.getAsJsonObject().addProperty(property, value);
    return this;
  }

  public JsonBuilder add(String property, Long value) {
    target.getAsJsonObject().addProperty(property, value);
    return this;
  }

  public JsonBuilder add(String property, Integer value) {
    target.getAsJsonObject().addProperty(property, value);
    return this;
  }

  public JsonBuilder add(String property, Double value){
    target.getAsJsonObject().addProperty(property, value);
    return this;
  }

  public JsonBuilder add(String property, boolean value) {
    target.getAsJsonObject().addProperty(property, value);
    return this;
  }

  public JsonBuilder add(String property, JsonBuilder value) {
    target.getAsJsonObject().add(property, value.asJsonElement());
    return this;
  }

  public JsonBuilder add(String property, Map<String, String> object) {
    JsonObject o = new JsonObject();
    for (String key : object.keySet()) {
      o.addProperty(key, object.get(key));
    }
    target.getAsJsonObject().add(property, o);
    return this;
  }

  public JsonBuilder add(String property, Set<?> values) {
    JsonArray array = new JsonArray();
    for (Object each : values) {
      if (each instanceof Long) {
        array.add(new JsonPrimitive((Long) each));
      }
      if (each instanceof String) {
        array.add(new JsonPrimitive((String) each));
      }
      if (each instanceof Boolean) {
        array.add(new JsonPrimitive((Boolean) each));
      }

    }
    target.getAsJsonObject().add(property, array);

    return this;
  }

  public JsonBuilder add(String property, List<String> values) {
    JsonArray array = new JsonArray();
    for (String each : values) {
      array.add(new JsonPrimitive(each));
    }
    target.getAsJsonObject().add(property, array);
    return this;
  }

  public JsonBuilder addIntegersAsList(String property, List<Integer> values) {
    JsonArray array = new JsonArray();
    for (Integer each : values) {
      array.add(new JsonPrimitive(each));
    }
    target.getAsJsonObject().add(property, array);
    return this;
  }

  public JsonElement asJsonElement() {
    return target;
  }


  public String build() {
    return GSON.toJson(target);
  }

  public String buildPrettyJson(){
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(target); }
}
