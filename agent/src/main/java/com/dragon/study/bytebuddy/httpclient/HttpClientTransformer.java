package com.dragon.study.bytebuddy.httpclient;

import com.dragon.study.bytebuddy.ClassInjector;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.pool.TypePool;

import java.net.URL;
import java.net.URLClassLoader;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * Created by dragon on 16/3/29.
 */
public class HttpClientTransformer implements AgentBuilder.Transformer {

  private final String httpInterceptor;

  private final URL[] urls;

  public HttpClientTransformer(String httpInterceptor, URL[] urls) {
    this.httpInterceptor = httpInterceptor;
    this.urls = urls;
  }

  @Override
  public DynamicType.Builder transform(DynamicType.Builder<?> builder,
      TypeDescription typeDescription, ClassLoader classLoader) {
    ClassInjector injector = new ClassInjector();
    try {
      injector.injectToURLClassLoader(urls, (URLClassLoader) classLoader);
    } catch (Exception e) {
      e.printStackTrace();
      return builder;
    }

    ClassFileLocator.Compound compound = new ClassFileLocator.Compound(ClassFileLocator.ForClassLoader.of(classLoader),
        ClassFileLocator.ForClassLoader.ofClassPath());
    return builder.method(named("execute")
        .and(returns(named("org.apache.http.client.methods.CloseableHttpResponse"))))
        .intercept(MethodDelegation.to(TypePool.Default.of(compound).describe(httpInterceptor).resolve()));
  }
}
