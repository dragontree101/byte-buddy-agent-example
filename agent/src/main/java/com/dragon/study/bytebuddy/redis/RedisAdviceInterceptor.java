package com.dragon.study.bytebuddy.redis;


import com.dragon.study.bytebuddy.Trace;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextRefreshedHolder;

import net.bytebuddy.asm.Advice;

import redis.clients.jedis.Connection;
import redis.clients.jedis.Protocol;

/**
 * Created by dragon on 16/5/4.
 */
public class RedisAdviceInterceptor {

  @Advice.OnMethodEnter
  static long enter(
      @Advice.This
      Object obj,
      @Advice.Argument(value = 0)
      Object arg0,
      @Advice.Argument(value = 1)
      byte[][] arg1) {

    Connection connection = (Connection) obj;
    String host = connection.getHost();
    int port = connection.getPort();

    Person person = ApplicationContextRefreshedHolder.getBean(Person.class);

    Protocol.Command command = (Protocol.Command)arg0;
    System.out.println(command);

    int i = 0;
    for (byte[] arg : arg1) {
      System.out.println("redis arg index: " + i++ + ", value is " + arg.toString());
    }

    System.out.println("local redis host is " + host + ", port is " + port );

    return System.currentTimeMillis();
  }

  @Advice.OnMethodExit(onThrowable = RuntimeException.class)
  static void exit(
      @Advice.Enter
      long startTime,
      @Advice.Thrown
      Throwable t) {
    Trace trace = new Trace();
    trace.setCost(System.currentTimeMillis() - startTime);
    if (t != null) {
      t.printStackTrace();
      trace.setE(new Exception(t));
      trace.setStatusCode(-1);
    }

    System.out.println("redis exit");

  }

}
