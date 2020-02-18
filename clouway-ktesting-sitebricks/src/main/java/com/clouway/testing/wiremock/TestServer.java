package com.clouway.testing.wiremock;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
* @author Miroslav Genov (miroslav.genov@clouway.com)
*/
public class TestServer {
  private int port;
  private Server server;

  private String response;
  private int responseCode = 200;

  public TestServer(int port) {
    this.port = port;
    server = new Server(port);
    Context root = new Context(server, "/", Context.ALL);

    root.addServlet(DefaultServlet.class, "/");
    root.addServlet(new ServletHolder(new HttpServlet() {

      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        resp.setStatus(responseCode);

        printWriter.print(response);
        printWriter.flush();
      }

      @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(responseCode);
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(response);
        printWriter.flush();
      }

    }), "/mytesturl");
    root.setResourceBase("web");
  }

  public String getTestEndpoint() {
    return "http://localhost:" + port + "/mytesturl";
  }

  public void start() {
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void pretendThatServiceReplaysWithStatus(int responseCode) {
    this.responseCode = responseCode;
  }

  public void pretendThatNextResponseWillBe(String response) {
    this.response = response;
  }

  public void pretendThatNextResponseWillBe(String response, int responseCode) {
    this.response = response;
    this.responseCode = responseCode;
  }
}
