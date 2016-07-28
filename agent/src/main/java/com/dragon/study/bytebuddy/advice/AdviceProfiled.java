package com.dragon.study.bytebuddy.advice;

import java.util.Random;

/**
 * Created by dragon on 16/4/18.
 */
public class AdviceProfiled implements IAdviceProfiled{

  public AdviceProfiled() {
  }

  public int profile(int sleepTime, String invalidParam) {
    try {
      Thread.sleep(sleepTime);
      System.out.println("---- profile sleep time ------");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

//    Random random = new Random();
//    if(random.nextInt(sleepTime) % 2 == 0) {
//      int i = 1/0;
//      System.out.println(i);
//    }
//    System.out.println("profile sleep time is " + sleepTime + ", invalid param is " + invalidParam);
    return sleepTime * 10;
  }

  public void exceptionProfile() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("exception profile");

    int i = 1/0;
    System.out.println(i);


  }


}
