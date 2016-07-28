package com.dragon.study.bytebuddy.context;


import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

//3
public class ApplicationPreparedHolder implements ApplicationListener<ApplicationPreparedEvent> {


  @Override
  public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
    System.out.println("application prepared event");
  }
}
