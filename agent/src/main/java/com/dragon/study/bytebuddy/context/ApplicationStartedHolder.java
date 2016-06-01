package com.dragon.study.bytebuddy.context;


import com.dragon.study.bytebuddy.DebugListener;
import com.dragon.study.bytebuddy.advice.AdviceProfiledTransformer;
import com.dragon.study.bytebuddy.advice.IAdviceProfiled;
import com.dragon.study.bytebuddy.jersey.RequestFilterAdviceTransformer;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import javax.ws.rs.container.ContainerRequestFilter;

import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class ApplicationStartedHolder implements ApplicationListener<ApplicationStartedEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
    System.out.println("application started event");
    ByteBuddyAgent.install();
    new AgentBuilder.Default()
        .with(DebugListener.getListener())
//        .type(named("javax.ws.rs.container.ContainerRequestFilter").and(not(isAbstract())))
        .type(isSubTypeOf(ContainerRequestFilter.class))
        .transform(new RequestFilterAdviceTransformer())
//        .type(isAnnotatedWith(EnableMetrics.class))
//        .transform(new MetricsAdviceTransformer())
//          .type((isSubTypeOf(IAdviceProfiled.class)))
//          .type(named("com.dragon.study.bytebuddy.advice.AdviceProfiled"))
//          .transform(new AdviceProfiledTransformer())
        .installOnByteBuddyAgent();
  }
}
