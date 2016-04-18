package com.dragon.study.bytebuddy.advice;

/**
 * Created by dragon on 16/4/18.
 */
public class AdviceProfiled {

  public AdviceProfiled() {
  }

  public void profile() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("profile");
  }
}
