package com.dragon.study.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.pool.TypePool;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * Created by dragon on 16/4/1.
 */
public abstract class AbstractTransformer  implements AgentBuilder.Transformer {

  protected final String interceptor;

  public AbstractTransformer(String interceptor) {
    this.interceptor = interceptor;
  }

  protected abstract DynamicType.Builder.MethodDefinition.ImplementationDefinition builderTransform(DynamicType.Builder<?> builder,  ClassFileLocator.Compound compound);

  @Override
  public DynamicType.Builder transform(DynamicType.Builder<?> builder,
      TypeDescription typeDescription, ClassLoader classLoader) {

    ClassFileLocator.Compound compound = new ClassFileLocator.Compound(ClassFileLocator.ForClassLoader.of(classLoader),
        ClassFileLocator.ForClassLoader.ofClassPath());

    return builderTransform(builder, compound).intercept(MethodDelegation.to(TypePool.Default.of(compound).describe(interceptor).resolve()));

  }

}
