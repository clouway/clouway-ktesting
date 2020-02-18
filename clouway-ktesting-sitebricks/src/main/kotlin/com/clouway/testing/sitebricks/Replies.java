package com.clouway.testing.sitebricks;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.sitebricks.client.Transport;
import com.google.sitebricks.headless.Reply;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public class Replies {

  public static JsonElement asJson(Reply<?> reply, Transport transport) {
    Object value = Matchers.property("entity", reply);
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    try {
      transport.out(bout, Object.class, value);
    } catch (IOException e) {
      throw new IllegalStateException("The reply cannot be serialized as JSON.");
    }
    return new JsonParser().parse(bout.toString());
  }

}
