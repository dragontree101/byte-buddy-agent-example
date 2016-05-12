package com.dragon.study.bytebuddy.context;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;


public class ApplicationContextStartedHolder implements ApplicationListener<ContextStartedEvent> {

  @Override
  public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
    System.out.println("context started event");
  }
}
