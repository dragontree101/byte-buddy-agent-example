package com.dragon.study.bytebuddy.okhttp;

import com.dragon.study.bytebuddy.Trace;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextRefreshedHolder;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.concurrent.Callable;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dragon on 16/3/29.
 */
public class OkHttpInterceptor {

  public static Response doProceed(
      @SuperCall
      Callable<Response> client,
      @AllArguments
      Object[] args) throws Exception {
    Person person = ApplicationContextRefreshedHolder.getBean(Person.class);
    Trace trace = new Trace();
    long start = System.currentTimeMillis();
    try {
      Request request = (Request)args[0];
      trace.setUrl(request.url().encodedPath());
      Response response = client.call();
      trace.setCost(System.currentTimeMillis() - start);
      trace.setStatusCode(response.code());
      System.out.println("trace is " + trace + ", person is " + person.toString());
      return response;
    } catch (Exception e) {
      trace.setCost(System.currentTimeMillis() - start);
      trace.setE(e);
      trace.setStatusCode(-1);
      System.out.println("exception trace is " + trace + ", person is " );
      throw e;
    }
  }



}
