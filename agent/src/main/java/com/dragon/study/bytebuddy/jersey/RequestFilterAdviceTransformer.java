package com.dragon.study.bytebuddy.jersey;


import com.dragon.study.bytebuddy.AbstractAdviceTransformer;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * Created by dragon on 16/4/18.
 */
public class RequestFilterAdviceTransformer extends AbstractAdviceTransformer {

  @Override
  protected Class<?> builderAdviceTransform() {
    return RequestFilterAdviceInterceptor.class;
  }

  @Override
  protected ElementMatcher<? super MethodDescription.InDefinedShape> description(
      ClassFileLocator.Compound compound) {
    return named("filter")
        .and(takesArguments(JerseyDispatcherDescription.containerRequestContextDescription()))
        .and(returns(TypeDescription.VOID));
  }

}
