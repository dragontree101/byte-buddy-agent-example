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
      @MetricsAdviceTransformer.CountSignature
      String countName) {
    Map<String, String> mapToExit = new HashMap<>();
    try {
      System.out.println("=======" + countName + "=======");
    } catch (Exception e) {
      e.printStackTrace();
    }
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
