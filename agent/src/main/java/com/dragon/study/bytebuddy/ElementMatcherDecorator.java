package com.dragon.study.bytebuddy;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * Created by dragon on 16/7/28.
 */
public class ElementMatcherDecorator<T> implements ElementMatcher<T> {

  private final ElementMatcher<T> delegate;

  public static <T> ElementMatcher<T> delegate(ElementMatcher<T> delegate) {
      return new ElementMatcherDecorator(delegate);
  }

  private ElementMatcherDecorator(ElementMatcher<T> delegate) {
    this.delegate = delegate;
  }


  @Override
  public boolean matches(T target) {
    return delegate.matches(target);
  }
}
