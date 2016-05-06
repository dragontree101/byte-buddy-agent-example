package com.dragon.study.bytebuddy.advice;

import net.bytebuddy.asm.Advice;

/**
 * Created by dragon on 16/4/18.
 */
class AdviceProfiledInterceptor {

  @Advice.OnMethodEnter
  static long enter(@Advice.Origin("#m") String methodName, @Advice.Argument(value = 0) int sleepTime, @Advice.Argument(value = 1) String invalidParam) {
    System.out.println("======= sleep time is " + sleepTime + ", invalid param is " + invalidParam + "=======");
    return System.nanoTime();
  }

  @Advice.OnMethodExit(onThrowable = RuntimeException.class)
  static void exit(@Advice.Enter long value, @Advice.Return int sleepTime, @Advice.Thrown Throwable t) {
    System.out.println("duration time is " + ((System.nanoTime() - value) / (1000 * 1000)) + "ms");
    if(t != null) {
      System.out.println("--------- exception is " + t.getMessage());
    } else {
      System.out.println("======== sleep time is " + sleepTime + "========");
    }
  }


  public static void main(String[] args) throws InterruptedException {
    long t1 = System.currentTimeMillis();
    long t2 = System.nanoTime();
    Thread.sleep(1000);

    System.out.println("System.currentTimeMillis() : " + (System.currentTimeMillis() - t1));
    System.out.println("System.nanoTime() : " + (System.nanoTime() - t2));

  }

}
