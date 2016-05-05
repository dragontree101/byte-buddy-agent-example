package com.dragon.study.bytebuddy.redis;


import com.dragon.study.bytebuddy.AbstractAdviceTransformer;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * Created by dragon on 16/4/18.
 */
public class RedisAdviceTransformer extends AbstractAdviceTransformer {

  @Override
  protected Class<?> builderAdviceTransform() {
    return RedisAdviceInterceptor.class;
  }

  @Override
  protected ElementMatcher<? super MethodDescription.InDefinedShape> description(
      ClassFileLocator.Compound compound) {
    return named("sendCommand").and(takesArguments(
        RedisTypeDescription.commandDescription(compound), RedisTypeDescription.byteArrayArrayDescription()))
        .and(returns(named("redis.clients.jedis.Connection")));
  }

}
