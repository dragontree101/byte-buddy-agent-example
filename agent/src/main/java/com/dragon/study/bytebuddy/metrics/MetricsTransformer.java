package com.dragon.study.bytebuddy.metrics;

import com.dragon.study.bytebuddy.AbstractTransformer;
import com.dragon.study.bytebuddy.annotation.Count;

import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * Created by dragon on 16/3/29.
 */
public class MetricsTransformer extends AbstractTransformer {

  public MetricsTransformer(String metricsInterceptor) {
    super(metricsInterceptor);
  }

  @Override
  protected DynamicType.Builder.MethodDefinition.ImplementationDefinition builderTransform(
      DynamicType.Builder<?> builder, ClassFileLocator.Compound compound) {
    return builder.method(isPublic().and(isAnnotatedWith(Count.class)));
  }

}
