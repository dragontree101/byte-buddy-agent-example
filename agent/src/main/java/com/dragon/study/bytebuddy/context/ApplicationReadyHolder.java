package com.dragon.study.bytebuddy.context;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;


public class ApplicationReadyHolder implements ApplicationListener<ApplicationReadyEvent> {


  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    System.out.println("application ready event");
  }
}
