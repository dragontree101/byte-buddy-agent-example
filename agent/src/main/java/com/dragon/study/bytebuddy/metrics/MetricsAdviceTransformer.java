package com.dragon.study.bytebuddy.metrics;


import com.dragon.study.bytebuddy.annotation.Count;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;

/**
 * Created by dragon on 16/4/18.
 */
public class MetricsAdviceTransformer implements AgentBuilder.Transformer {

//  @Override
//  protected Class<?> builderAdviceTransform() {
//    return MetricsAdviceInterceptor.class;
//  }
//
//  @Override
//  protected ElementMatcher<? super MethodDescription.InDefinedShape> description(
//      ClassFileLocator.Compound compound) {
//    return isPublic().and(isAnnotatedWith(Count.class));
//  }

  private AsmVisitorWrapper.ForDeclaredMethods advice = registerDynamicValues()
      .to(MetricsAdviceInterceptor.class)
      .on(isPublic().and(isAnnotatedWith(Count.class)));

  private Advice.WithCustomMapping registerDynamicValues() {
    List<MyAgentDynamicValue<?>> dynamicValues = getDynamicValues();
    Advice.WithCustomMapping withCustomMapping = Advice.withCustomMapping();
    for (MyAgentDynamicValue dynamicValue : dynamicValues) {
      withCustomMapping = withCustomMapping.bind(dynamicValue.getAnnotationClass(), dynamicValue);
    }
    return withCustomMapping;
  }

  protected List<MyAgentDynamicValue<?>> getDynamicValues() {
    return Collections.<MyAgentDynamicValue<?>>singletonList(new CountDynamicValue());
  }

  public class CountDynamicValue extends MyAgentDynamicValue<CountSignature> {

    @Override
    public Class<CountSignature> getAnnotationClass() {
      return CountSignature.class;
    }

    @Override
    public Object resolve(MethodDescription.InDefinedShape instrumentedMethod,
        ParameterDescription.InDefinedShape target,
        AnnotationDescription.Loadable<CountSignature> annotation, boolean initialized) {
      return getRequestName(instrumentedMethod);
    }

    private Object getRequestName(MethodDescription.InDefinedShape instrumentedMethod) {
      return instrumentedMethod.getDeclaredAnnotations().ofType(Count.class).loadSilent().name();
    }
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.PARAMETER)
  public @interface CountSignature {
  }


  public abstract static class MyAgentDynamicValue<T extends Annotation> implements Advice.DynamicValue<T> {
    public abstract Class<T> getAnnotationClass();
  }

  @Override
  public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
      TypeDescription typeDescription, ClassLoader classLoader) {

    return builder.visit(advice);
  }
}
