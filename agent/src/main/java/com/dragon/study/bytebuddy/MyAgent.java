package com.dragon.study.bytebuddy;

import com.dragon.study.bytebuddy.annotation.EnableMetrics;
import com.dragon.study.bytebuddy.metrics.MetricsTransformer;
import com.dragon.study.bytebuddy.mysql.MysqlTransformer;
import com.dragon.study.bytebuddy.okhttp.OkHttpTransformer;
import com.dragon.study.bytebuddy.redis.RedisTransformer;
import com.dragon.study.bytebuddy.thrift.ThriftServerTransformer;

import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * Created by dragon on 16/3/29.
 */
public class MyAgent {

  public static void premain(String arg, Instrumentation instrumentation) {

    String okHttpInterceptor = "com.dragon.study.bytebuddy.okhttp.OkHttpInterceptor";
    String redisInterceptor = "com.dragon.study.bytebuddy.redis.RedisInterceptor";
    String metricsInterceptor = "com.dragon.study.bytebuddy.metrics.MetricsInterceptor";
    String mysqlInterceptor = "com.dragon.study.bytebuddy.mysql.MysqlInterceptor";
    String thriftServerInterceptor = "com.dragon.study.bytebuddy.thrift.ThriftServerInterceptor";


    new AgentBuilder.Default()
            .with(DebugListener.getListener())
            .type(named("okhttp3.internal.http.HttpEngine$NetworkInterceptorChain"))
            .transform(new OkHttpTransformer(okHttpInterceptor))
            .type(nameStartsWith("redis.clients.jedis").and(not(isInterface())).and(not(isStatic())))
            .transform(new RedisTransformer(redisInterceptor))
            .type(isAnnotatedWith(EnableMetrics.class))
            .transform(new MetricsTransformer(metricsInterceptor))
            .type(named("com.mysql.jdbc.MysqlIO"))
            .transform(new MysqlTransformer(mysqlInterceptor))
            .type(named("org.apache.thrift.TBaseProcessor").or(named("org.apache.thrift.TBaseAsyncProcessor")))
            .transform(new ThriftServerTransformer(thriftServerInterceptor))
            .installOn(instrumentation);

  }


}
