package com.dragon.study.bytebuddy.jersey;


import com.dragon.study.bytebuddy.AbstractTransformer;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;

import static net.bytebuddy.matcher.ElementMatchers.isFinal;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * Created by dragon on 16/3/29.
 */
public class JerseyDispatcherTransformer extends AbstractTransformer {

  public JerseyDispatcherTransformer(String httpServletInterceptor) {
    super(httpServletInterceptor);
  }

  @Override
  protected DynamicType.Builder.MethodDefinition.ImplementationDefinition builderTransform(
      DynamicType.Builder<?> builder, ClassFileLocator.Compound compound) {
    return builder.method(named("dispatch").and(isFinal()).and(
        takesArguments(TypeDescription.OBJECT,
            JerseyDispatcherDescription.containerRequestDescription()))
        .and(returns(named("javax.ws.rs.core.Response"))));
  }
}
