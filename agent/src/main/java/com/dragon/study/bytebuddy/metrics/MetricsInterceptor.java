package com.dragon.study.bytebuddy.metrics;

import com.dragon.study.bytebuddy.annotation.Count;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextRefreshedHolder;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by dragon on 16/3/29.
 */
public class MetricsInterceptor {

  @RuntimeType
  public static Object metrics(
      @SuperCall
      Callable<?> client,
      @Origin
      Method method) throws Exception {
    Person person = ApplicationContextRefreshedHolder.getBean(Person.class);
    long start = System.currentTimeMillis();
    try {
      System.out.println("person is " + person.toString());
      return client.call();
    } finally {
      long end = System.currentTimeMillis();
      Count countAnnotation = method.getAnnotation(Count.class);
      String countName = countAnnotation.name();
      System.out.println("Call to " + countName + " took " + (end - start) + " ns");
    }
  }


}
