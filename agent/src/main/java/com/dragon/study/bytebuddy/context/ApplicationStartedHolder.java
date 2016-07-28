package com.dragon.study.bytebuddy.context;


import com.dragon.study.bytebuddy.DebugListener;
import com.dragon.study.bytebuddy.advice.AdviceProfiledTransformer;
import com.dragon.study.bytebuddy.advice.IAdviceProfiled;
import com.dragon.study.bytebuddy.jersey.RequestFilterAdviceTransformer;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.lang.instrument.Instrumentation;

import javax.ws.rs.container.ContainerRequestFilter;

import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.isBootstrapClassLoader;
import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.any;

import static com.dragon.study.bytebuddy.ElementMatcherDecorator.delegate;

//1
public class ApplicationStartedHolder implements ApplicationListener<ApplicationStartedEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
    System.out.println("application started event");
//    Instrumentation instrumentation;
//    try {
//      instrumentation = ByteBuddyAgent.getInstrumentation();
//    } catch (IllegalStateException e) {
//      instrumentation = ByteBuddyAgent.install(
//          new ByteBuddyAgent.AttachmentProvider.Compound(
//              ByteBuddyAgent.AttachmentProvider.DEFAULT));
//    }
    ByteBuddyAgent.install();
    new AgentBuilder.Default()
        .with(DebugListener.getListener())
//        .with(AgentBuilder.InitializationStrategy.SelfInjection.EAGER)
//        .ignore(any(), delegate(isBootstrapClassLoader()))
//        .or(nameStartsWith("com.sun."))
//        .or(nameStartsWith("sun."))
//        .or(nameStartsWith("jdk."))
//        .or(nameStartsWith("org.aspectj."))
//        .or(nameStartsWith("org.groovy."))
//        .or(nameStartsWith("com.p6spy."))
//        .or(nameStartsWith("net.bytebuddy."))
//        .or(nameStartsWith("org.springframework"))
//        .or(nameStartsWith("org.slf4j.").and(not(nameStartsWith("org.slf4j.impl."))))
//        .or(nameContains("javassist"))
//        .or(nameContains(".asm."))
//        .disableClassFormatChanges()
//        .type(named("javax.ws.rs.container.ContainerRequestFilter").and(not(isAbstract())))
//        .type(isSubTypeOf(ContainerRequestFilter.class))
//        .transform(new RequestFilterAdviceTransformer())
//        .type(isAnnotatedWith(EnableMetrics.class))
//        .transform(new MetricsAdviceTransformer())
//          .type((isSubTypeOf(IAdviceProfiled.class)))
          .type(named("com.dragon.study.bytebuddy.advice.AdviceProfiled"))
//          .type(transformer.getMatcher())
          .transform(new AdviceProfiledTransformer())
//        .installOn(instrumentation);
        .installOnByteBuddyAgent();
  }
}
