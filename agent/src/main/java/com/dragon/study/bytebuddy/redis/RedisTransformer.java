package com.dragon.study.bytebuddy.redis;

import com.dragon.study.bytebuddy.AbstractTransformer;
import com.dragon.study.bytebuddy.utils.ClassInjector;

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
public class RedisTransformer extends AbstractTransformer {

  public RedisTransformer(String redisInterceptor) {
    super(redisInterceptor);
  }

  @Override
  protected DynamicType.Builder.MethodDefinition.ImplementationDefinition builderTransform(
      DynamicType.Builder<?> builder, ClassFileLocator.Compound compound) {

    return builder.method(named("sendCommand").and(takesArguments(
        RedisTypeDescription.commandDescription(compound),
        RedisTypeDescription.byteArrayArrayDescription()))
        .and(returns(named("redis.clients.jedis.Connection"))));
  }

}
