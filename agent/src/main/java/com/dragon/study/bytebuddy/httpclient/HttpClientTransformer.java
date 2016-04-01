package com.dragon.study.bytebuddy.httpclient;

import com.dragon.study.bytebuddy.AbstractTransformer;

import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * Created by dragon on 16/3/29.
 */
public class HttpClientTransformer extends AbstractTransformer {

  public HttpClientTransformer(String httpInterceptor) {
    super(httpInterceptor);
  }

  @Override
  protected DynamicType.Builder.MethodDefinition.ImplementationDefinition builderTransform(
      DynamicType.Builder<?> builder, ClassFileLocator.Compound compound) {
    return builder.method(named("execute")
        .and(returns(named("org.apache.http.client.methods.CloseableHttpResponse"))));
  }

}
