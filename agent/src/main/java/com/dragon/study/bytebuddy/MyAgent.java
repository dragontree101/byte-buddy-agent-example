package com.dragon.study.bytebuddy;

import com.dragon.study.bytebuddy.httpclient.HttpClientTransformer;
import com.dragon.study.bytebuddy.redis.RedisTransformer;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.pool.TypePool;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Created by dragon on 16/3/29.
 */
public class MyAgent {

  public static void premain(String arg, Instrumentation instrumentation) {

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader().getParent();
    ClassFileLocator.Compound compound = new ClassFileLocator.Compound(ClassFileLocator.ForClassLoader.of(classLoader),
        ClassFileLocator.ForClassLoader.ofClassPath());

    String httpInterceptor = "com.dragon.study.bytebuddy.httpclient.HttpClientInterceptor";
    String redisInterceptor = "com.dragon.study.bytebuddy.redis.RedisInterceptor";

    new AgentBuilder.Default()
//        .with(DebugListener.getListener())
        .type(named("org.apache.http.impl.client.CloseableHttpClient"))
        .transform(new HttpClientTransformer(TypePool.Default.of(compound).describe(httpInterceptor).resolve()))
        .type(named("redis.clients.jedis.Client"))
        .transform(new RedisTransformer(TypePool.Default.of(compound).describe(redisInterceptor).resolve()))
        .installOn(instrumentation);


  }
}
