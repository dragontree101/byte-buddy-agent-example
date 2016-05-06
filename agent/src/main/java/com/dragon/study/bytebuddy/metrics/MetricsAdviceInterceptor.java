package com.dragon.study.bytebuddy.metrics;

import com.dragon.study.bytebuddy.annotation.Count;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dragon on 16/5/4.
 */
public class MetricsAdviceInterceptor {

  @Advice.OnMethodEnter
  static Map<String, String> enter(
      @Advice.This
      Object thiz,
      @Advice.Origin("#m")
      String methodName) {
    Map<String, String> mapToExit = new HashMap<>();
    try {
      Method method = thiz.getClass().getMethod(methodName);
      Count countAndTimeAnnotation = method.getAnnotation(Count.class);
      System.out.println(countAndTimeAnnotation.name() + "=====");
    } catch (Exception e) {
      e.printStackTrace();
    }
//    Count countAndTimeAnnotation = method.getAnnotation(Count.class);
//    System.out.println(method.toString() + "-------");
    mapToExit.put("startTime", String.valueOf(System.currentTimeMillis()));
    return mapToExit;
  }

  @Advice.OnMethodExit(onThrowable = Throwable.class)
  static void exit(
      @Advice.Enter
      Map<String, String> enterParam) {
    long durationTime = System.currentTimeMillis() - Long.valueOf(enterParam.get("startTime"));
    System.out.println(durationTime);
  }
}
