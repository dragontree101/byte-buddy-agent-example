package com.dragon.study.bytebuddy.context;


import com.dragon.study.bytebuddy.DebugListener;
import com.dragon.study.bytebuddy.annotation.EnableMetrics;
import com.dragon.study.bytebuddy.metrics.MetricsAdviceTransformer;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;


public class ApplicationStartedHolder implements ApplicationListener<ApplicationStartedEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
    System.out.println("application started event");
    ByteBuddyAgent.install();
    new AgentBuilder.Default()
        .with(DebugListener.getListener())
        .type(isAnnotatedWith(EnableMetrics.class))
        .transform(new MetricsAdviceTransformer())
        .installOnByteBuddyAgent();
  }
}
