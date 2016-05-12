package com.dragon.study.bytebuddy.redis;

import com.dragon.study.bytebuddy.Trace;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextRefreshedHolder;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.concurrent.Callable;

import redis.clients.jedis.Connection;

/**
 * Created by dragon on 16/3/29.
 */
public class RedisInterceptor {

  public static Connection sendMessage(
      @SuperCall
      Callable<Connection> client,
      @AllArguments
      Object[] args) throws Exception {

    Person person = ApplicationContextRefreshedHolder.getBean(Person.class);
    Trace trace = new Trace();
    long start = System.currentTimeMillis();

    try {
      int i = 0;
      for (Object arg : args) {
        System.out.println("redis arg index: " + i++ + ", value is " + arg.toString());
      }

      trace.setUrl("local redis");
      Connection response = client.call();
      trace.setCost(System.currentTimeMillis() - start);
      trace.setStatusCode(200);
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
}
