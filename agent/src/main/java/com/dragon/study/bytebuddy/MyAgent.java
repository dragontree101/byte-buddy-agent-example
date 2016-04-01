package com.dragon.study.bytebuddy;

import com.dragon.study.bytebuddy.httpclient.HttpClientTransformer;
import com.dragon.study.bytebuddy.redis.RedisTransformer;

import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * Created by dragon on 16/3/29.
 */
public class MyAgent {

  public static void premain(String arg, Instrumentation instrumentation) {

    String httpInterceptor = "com.dragon.study.bytebuddy.httpclient.HttpClientInterceptor";
    String redisInterceptor = "com.dragon.study.bytebuddy.redis.RedisInterceptor";

    new AgentBuilder.Default()
            .with(DebugListener.getListener())
            .type(nameStartsWith("org.apache.http").and(not(isInterface())))
            .transform(new HttpClientTransformer(httpInterceptor))
            .type(nameStartsWith("redis.clients.jedis").and(not(isInterface())).and(not(isStatic())))
            .transform(new RedisTransformer(redisInterceptor))
            .installOn(instrumentation);

  }


}
