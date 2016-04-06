package com.dragon.study.bytebuddy;

import com.dragon.study.bytebuddy.okhttp.OkHttpTransformer;
import com.dragon.study.bytebuddy.redis.RedisTransformer;

import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Created by dragon on 16/3/29.
 */
public class MyAgent {

  public static void premain(String arg, Instrumentation instrumentation) {

    String okHttpInterceptor = "com.dragon.study.bytebuddy.okhttp.OkHttpInterceptor";
    String redisInterceptor = "com.dragon.study.bytebuddy.redis.RedisInterceptor";

    new AgentBuilder.Default()
            .with(DebugListener.getListener())
            .type(named("okhttp3.internal.http.HttpEngine$NetworkInterceptorChain"))
            .transform(new OkHttpTransformer(okHttpInterceptor))
            .type(nameStartsWith("redis.clients.jedis").and(not(isInterface())).and(not(isStatic())))
            .transform(new RedisTransformer(redisInterceptor))
            .installOn(instrumentation);

  }


}
