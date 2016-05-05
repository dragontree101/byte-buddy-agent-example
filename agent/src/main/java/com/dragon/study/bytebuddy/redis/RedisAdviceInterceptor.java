package com.dragon.study.bytebuddy.redis;


import com.dragon.study.bytebuddy.Trace;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextHolder;

import net.bytebuddy.asm.Advice;

import redis.clients.jedis.Connection;
import redis.clients.jedis.Protocol;

/**
 * Created by dragon on 16/5/4.
 */
class RedisAdviceInterceptor {

  @Advice.OnMethodEnter
  static long enter(
      @Advice.This
      Connection connection,
      @Advice.Argument(value = 0)
      Protocol.Command command,
      @Advice.Argument(value = 1)
      byte[][] args) {

    String host = connection.getHost();
    int port = connection.getPort();

    Person person = ApplicationContextHolder.getBean(Person.class);
    Trace trace = new Trace();

    int i = 0;
    for (Object arg : args) {
      System.out.println("redis arg index: " + i++ + ", value is " + arg.toString());
    }

    trace.setUrl("local redis host is " + host + ", port is " + port + ", person is " + person);
    return System.currentTimeMillis();
  }

  @Advice.OnMethodExit(onThrowable = RuntimeException.class)
  static void exit(
      @Advice.Enter
      long startTime,
      @Advice.Thrown
      Exception e) {
    Trace trace = new Trace();
    trace.setCost(System.currentTimeMillis() - startTime);
    if (e != null) {
      e.printStackTrace();
      trace.setE(e);
      trace.setStatusCode(-1);
    }

    System.out.println("redis exit");

  }



}
