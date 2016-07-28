package com.dragon.study.bytebuddy.context;


import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

//2
public class ApplicationEnvStartedHolder implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


  @Override
  public void onApplicationEvent(
      ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
    System.out.println("application env prepared event");
  }
}
