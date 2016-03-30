package com.dragon.study.bytebuddy.httpclient;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * Created by dragon on 16/3/29.
 */
public class HttpClientTransformer implements AgentBuilder.Transformer {

  private final TypeDescription delegate;

  public HttpClientTransformer(TypeDescription delegate) {
    this.delegate = delegate;
  }

  @Override
  public DynamicType.Builder transform(DynamicType.Builder<?> builder,
      TypeDescription typeDescription, ClassLoader classLoader) {
    return builder.method(named("execute")
        .and(returns(named("org.apache.http.client.methods.CloseableHttpResponse"))))
        .intercept(MethodDelegation.to(delegate));
  }
}
