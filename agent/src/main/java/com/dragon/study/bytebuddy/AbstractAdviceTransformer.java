package com.dragon.study.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * Created by dragon on 16/4/1.
 */
public abstract class AbstractAdviceTransformer implements AgentBuilder.Transformer {

  protected abstract Class<?> builderAdviceTransform();

  protected abstract ElementMatcher<? super MethodDescription.InDefinedShape> description(
      ClassFileLocator.Compound compound);

  @Override
  public DynamicType.Builder transform(DynamicType.Builder<?> builder,
      TypeDescription typeDescription, ClassLoader classLoader) {

    ClassFileLocator.Compound compound = new ClassFileLocator.Compound(
        ClassFileLocator.ForClassLoader.of(classLoader),
        ClassFileLocator.ForClassLoader.ofClassPath());


    return builder.visit(Advice.to(builderAdviceTransform()).on(description(compound)));

  }

}
