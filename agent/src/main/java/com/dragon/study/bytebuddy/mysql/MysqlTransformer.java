package com.dragon.study.bytebuddy.mysql;

import com.dragon.study.bytebuddy.AbstractTransformer;
import com.dragon.study.bytebuddy.redis.RedisTypeDescription;

import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;

import static net.bytebuddy.matcher.ElementMatchers.isFinal;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * Created by dragon on 16/3/29.
 */
public class MysqlTransformer extends AbstractTransformer {

  public MysqlTransformer(String mysqlInterceptor) {
    super(mysqlInterceptor);
  }

  @Override
  protected DynamicType.Builder.MethodDefinition.ImplementationDefinition builderTransform(
      DynamicType.Builder<?> builder, ClassFileLocator.Compound compound) {
    return builder.method(isFinal().and(named("sqlQueryDirect"))
        .and(returns(named("com.mysql.jdbc.ResultSetInternalMethods"))));
  }

}
