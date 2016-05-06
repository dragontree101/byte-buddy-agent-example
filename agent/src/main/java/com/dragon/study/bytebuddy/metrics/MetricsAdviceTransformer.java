package com.dragon.study.bytebuddy.metrics;


import com.dragon.study.bytebuddy.AbstractAdviceTransformer;
import com.dragon.study.bytebuddy.annotation.Count;
import com.dragon.study.bytebuddy.redis.RedisAdviceInterceptor;
import com.dragon.study.bytebuddy.redis.RedisTypeDescription;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * Created by dragon on 16/4/18.
 */
public class MetricsAdviceTransformer extends AbstractAdviceTransformer {

  @Override
  protected Class<?> builderAdviceTransform() {
    return MetricsAdviceInterceptor.class;
  }

  @Override
  protected ElementMatcher<? super MethodDescription.InDefinedShape> description(
      ClassFileLocator.Compound compound) {
    return isPublic().and(isAnnotatedWith(Count.class));
  }

}
