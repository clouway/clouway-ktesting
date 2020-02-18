package com.clouway.testing.sitebricks;

import com.clouway.telcong.testing.JsonBuilder;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.inject.TypeLiteral;
import com.google.sitebricks.client.Transport;
import com.google.sitebricks.headless.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Miroslav Genov (miroslav.genov@clouway.com)
 */
public class FakeRequest implements Request {

  public static class Builder {
    private final JsonBuilder jsonBuilder;
    private Transport transport;

    public Builder(JsonBuilder jsonBuilder) {
      this.jsonBuilder = jsonBuilder;
    }

    public Builder transport(Transport transport) {
      this.transport = transport;
      return this;
    }

    public FakeRequest build() {
      return new FakeRequest(jsonBuilder, transport);
    }
  }

  private final String body;
  private Transport transport;
  public ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
  public ImmutableListMultimap.Builder<String, String> multiParams = ImmutableListMultimap.builder();

  public FakeRequest(JsonBuilder body) {
    this(body, null);
  }

  public FakeRequest(String body) {
    this.body = body;
  }

  public FakeRequest(JsonBuilder body, Transport transport) {
    this.body = body.build();
    this.transport = transport;
  }

  public static Builder newRequest(JsonBuilder jsonBuilder) {
    return new Builder(jsonBuilder);
  }

  public static FakeRequest newRequest() {
    return new FakeRequest(JsonBuilder.aNewJson(), null);
  }

  public FakeRequest parameter(String name, String value) {
    this.params.put(name, value);
    return this;
  }

  public FakeRequest parameter(String name, Long value) {
      this.params.put(name, value.toString());
      return this;
    }

  public FakeRequest parameter(String name, Iterable<String> values) {
    this.multiParams.putAll(name, values);
    return this;
  }

  @Override
  public <E> RequestRead<E> read(final Class<E> type) {
    return new RequestRead<E>() {
      @Override
      public E as(Class<? extends Transport> transport) {
          try {
            Transport transportInstance = FakeRequest.this.transport;
            if(FakeRequest.this.transport == null) {
              transportInstance = transport.newInstance();
            }
            return transportInstance.in(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)), type);
          } catch (InstantiationException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        return null;
      }
    };
  }

  @Override
  public <E> RequestRead<E> read(final TypeLiteral<E> type) {
    return new RequestRead<E>() {
      @Override
      public E as(Class<? extends Transport> transport) {
        try {
          Transport transportInstance = FakeRequest.this.transport;
          if(FakeRequest.this.transport == null) {
            transportInstance = transport.newInstance();
          }
          return transportInstance.in(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)), type);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }
    };
  }

  @Override
  public void readTo(OutputStream out) throws IOException {

  }

  @Override
  public Multimap<String, String> headers() {
    return null;
  }

  @Override
  public Multimap<String, String> params() {
    return multiParams.build();
  }

  @Override
  public Multimap<String, String> matrix() {
    return null;
  }

  @Override
  public String matrixParam(String name) {
    return null;
  }

  @Override
  public String param(String name) {
    return params.build().get(name);
  }

  @Override
  public String header(String name) {
    return null;
  }

  @Override
  public String uri() {
    return null;
  }

  @Override
  public String path() {
    return null;
  }

  @Override
  public String context() {
    return null;
  }

  @Override
  public String method() {
    return null;
  }

  @Override
  public void validate(Object obj) {

  }
}
