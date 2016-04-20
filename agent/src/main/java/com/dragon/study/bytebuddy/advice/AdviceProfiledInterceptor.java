package com.dragon.study.bytebuddy.advice;

import net.bytebuddy.asm.Advice;

/**
 * Created by dragon on 16/4/18.
 */
class AdviceProfiledInterceptor {

  @Advice.OnMethodEnter
  static long enter(@Advice.Origin("#t.#m") String signature) {
    System.out.println("enter advice signature is " + signature);
    return System.nanoTime();
  }

  @Advice.OnMethodExit
  static void exit(@Advice.Enter long value, @Advice.Thrown Throwable t) {
    System.out.println("duration time is " + ((System.nanoTime() - value) / (1000 * 1000)) + "ms");
    System.out.println("--------- exception is " + t.getMessage());
  }

  public static void main(String[] args) throws InterruptedException {
    long t1 = System.currentTimeMillis();
    long t2 = System.nanoTime();
    Thread.sleep(1000);

    System.out.println("System.currentTimeMillis() : " + (System.currentTimeMillis() - t1));
    System.out.println("System.nanoTime() : " + (System.nanoTime() - t2));

  }

}
