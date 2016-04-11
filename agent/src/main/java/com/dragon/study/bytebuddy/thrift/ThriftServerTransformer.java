package com.dragon.study.bytebuddy.thrift;

import com.dragon.study.bytebuddy.AbstractTransformer;
import com.dragon.study.bytebuddy.redis.RedisTypeDescription;

import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * Created by dragon on 16/3/29.
 */
public class ThriftServerTransformer extends AbstractTransformer {

  public ThriftServerTransformer(String thriftServerInterceptor) {
    super(thriftServerInterceptor);
  }

  @Override
  protected DynamicType.Builder.MethodDefinition.ImplementationDefinition builderTransform(
      DynamicType.Builder<?> builder, ClassFileLocator.Compound compound) {
    return builder.method(named("process").and(
        takesArguments(ThriftTypeDescription.protocolDescription(), ThriftTypeDescription.protocolDescription())
            .or(takesArguments(ThriftTypeDescription.asyncFrameBufferDescription())))
        .and(returns(ThriftTypeDescription.boleanDescription())));
  }

}
