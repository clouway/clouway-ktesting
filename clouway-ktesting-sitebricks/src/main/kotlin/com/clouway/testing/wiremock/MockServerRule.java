package com.clouway.testing.wiremock;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

/**
 * @author Georgi Georgiev (georgi.georgiev@clouway.com)
 */
public class MockServerRule extends WireMockRule {

  private Integer port = 0;

  //generate random port for every test
  public MockServerRule() {
    super(WireMockConfiguration.wireMockConfig().dynamicPort());
  }

  public MockServerRule(int port) {
    super(port);
  }

  @Override
  protected void before() {
    super.before();
    port = this.port();
  }

  public String getHost() {
    return "http://localhost:" + port.toString();
  }
}
