package com.dragon.study.bytebuddy.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dragon on 16/3/30.
 */
public class ClassInjector {

  private static Method ADD_URL;
  private static Set<URL> URL_SET = new HashSet<>();

  static {
    try {
      ADD_URL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      ADD_URL.setAccessible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean injectToURLClassLoader(URL[] urls, URLClassLoader classLoader) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
    if (urls != null) {
      for (URL url : urls) {
        if(!URL_SET.contains(url)) {
          ADD_URL.invoke(classLoader, url);
          URL_SET.add(url);
          return true;
        }
      }
    }
    return false;
  }
}
