package com.dragon.study.bytebuddy.redis;

import com.dragon.study.bytebuddy.ClassInjector;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.pool.TypePool;

import java.net.URL;
import java.net.URLClassLoader;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * Created by dragon on 16/3/29.
 */
public class RedisTransformer implements AgentBuilder.Transformer {

  private final String redisInterceptor;

  private final URL[] urls;

  public RedisTransformer(String redisInterceptor, URL[] urls) {
    this.redisInterceptor = redisInterceptor;
    this.urls = urls;
  }

  @Override
  public DynamicType.Builder transform(DynamicType.Builder<?> builder,
      TypeDescription typeDescription, ClassLoader classLoader) {

    ClassInjector injector = new ClassInjector();
    try {
      injector.injectToURLClassLoader(urls, (URLClassLoader) classLoader);
    } catch (Exception e) {
      e.printStackTrace();
      return builder;
    }

    ClassFileLocator.Compound compound = new ClassFileLocator.Compound(
        ClassFileLocator.ForClassLoader.of(classLoader),
        ClassFileLocator.ForClassLoader.ofClassPath());

    return builder.method(named("sendCommand").and(takesArguments(
        RedisTypeDescription.commandDescription(compound),
        RedisTypeDescription.byteArrayArrayDescription()))
        .and(returns(named("redis.clients.jedis.Connection")))).intercept(
        MethodDelegation.to(TypePool.Default.of(compound).describe(redisInterceptor).resolve()));
  }
}
