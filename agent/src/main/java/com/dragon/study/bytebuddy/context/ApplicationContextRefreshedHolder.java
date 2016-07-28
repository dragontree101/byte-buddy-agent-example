package com.dragon.study.bytebuddy.context;


import com.dragon.study.bytebuddy.exception.LoadMyAgentException;
import com.dragon.study.bytebuddy.utils.ClassInjector;
import com.dragon.study.bytebuddy.utils.JarLoader;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//4
public class ApplicationContextRefreshedHolder implements ApplicationListener<ContextRefreshedEvent> {

  private static ApplicationContext _context;

  private static Map<Class, Object> mockBeans;

  public static ApplicationContext getApplicatioinContext() {
    return _context;
  }


  @SuppressWarnings("unchecked")
  public static <T> T getBean(Class<T> clazz) {
    T bean = null;
    if (mockBeans != null) {
      bean = (T) mockBeans.get(clazz);
    }
    if (bean != null) {
      return bean;
    }


    String[] names = _context.getBeanNamesForType(clazz);
    if (names == null || names.length == 0) {
      return null;
    }
    return (T) _context.getBean(names[0]);
  }


  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    System.out.println("context refreshed event");
    _context = contextRefreshedEvent.getApplicationContext();

    if(!loaderAgentJarToClassLoader()){
      System.out.println("use byte buddy agent, not use -javaagent");
    }
  }



  private boolean loaderAgentJarToClassLoader() {
    URL[] urls = JarLoader.loadMyAgentCoreLib();
    if(urls == null) {
      System.err.println("can not find my agent urls");
      return false;
    }

    ClassLoader classLoader = getClass().getClassLoader();

    ClassInjector injector = new ClassInjector();
    try {
      System.out.println("--------" + classLoader.toString());
      injector.injectToURLClassLoader(urls, (URLClassLoader) classLoader);
    } catch (Exception e) {
      e.printStackTrace();
      throw new LoadMyAgentException("inject url to class loader exception", e);
    }
    return true;
  }

}
