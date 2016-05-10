package com.dragon.study.bytebuddy.metrics;


import com.dragon.study.bytebuddy.AbstractAdviceTransformer;
import com.dragon.study.bytebuddy.annotation.Count;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;

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

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.PARAMETER)
  @interface MetricsSignature {
  }

  public class MetricsDynamicValue extends MyAgentDynamicValue<MetricsSignature> {

    @Override
    public Class<MetricsSignature> getAnnotationClass() {
      return MetricsSignature.class;
    }

    @Override
    public Object resolve(MethodDescription.InDefinedShape instrumentedMethod,
        ParameterDescription.InDefinedShape target,
        AnnotationDescription.Loadable<MetricsSignature> annotation, boolean initialized) {
      return getAnnotationValue(instrumentedMethod);
    }

    private Object getAnnotationValue(MethodDescription.InDefinedShape instrumentedMethod) {
      return instrumentedMethod.getDeclaredAnnotations().ofType(Count.class).loadSilent().name();
    }
  }

  protected List<MyAgentDynamicValue<?>> getDynamicValues() {
    return Collections.<MyAgentDynamicValue<?>>singletonList(new MetricsDynamicValue());
  }
}
