package com.dragon.study.bytebuddy.httpclient;

import com.dragon.study.bytebuddy.Trace;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextHolder;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.concurrent.Callable;

/**
 * Created by dragon on 16/3/29.
 */
public class HttpClientInterceptor {

  public static CloseableHttpResponse doExecute(
      @SuperCall
      Callable<CloseableHttpResponse> client,
      @AllArguments
      Object[] args) throws Exception {
    Person person = ApplicationContextHolder.getBean(Person.class);
    Trace trace = new Trace();
    long start = System.currentTimeMillis();
    try {
      trace.setUrl(extractRequestUrl(args));
      CloseableHttpResponse response = client.call();
      trace.setCost(System.currentTimeMillis() - start);
      trace.setStatusCode(response.getStatusLine().getStatusCode());
      System.out.println("trace is " + trace + ", person is " + person.toString());
      return response;
    } catch (Exception e) {
      trace.setCost(System.currentTimeMillis() - start);
      trace.setE(e);
      trace.setStatusCode(-1);
      System.out.println("exception trace is " + trace + ", person is " + person.toString());
      throw e;
    }
  }

  private static String extractRequestUrl(Object[] args) {
    if (args[0] instanceof HttpHost) {
      HttpHost host = (HttpHost) args[0];
      HttpRequest request = (HttpRequest) args[1];
      return getRequestUrl(host, request);
    } else if (args[0] instanceof HttpUriRequest) {
      HttpUriRequest request = (HttpUriRequest) args[0];
      if (request != null && request.getURI() != null) {
        StringBuilder builder = new StringBuilder(1024);
        builder.append(request.getMethod()).append(" ").append(request.getURI().toString());
        return builder.toString();
      } else {
        return "";
      }
    } else {
      return "";
    }
  }

  private static String getRequestUrl(HttpHost host, HttpRequest request) {
    StringBuilder builder = new StringBuilder(1024);
    if (request != null && request.getRequestLine() != null && host != null) {
      RequestLine requestLine = request.getRequestLine();
      builder.append(requestLine.getMethod()).append(" ").append(host.toURI())
          .append(requestLine.getUri());
    }
    return builder.toString();
  }

}
