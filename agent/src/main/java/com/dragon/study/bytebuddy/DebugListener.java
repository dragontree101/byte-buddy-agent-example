package com.dragon.study.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

/**
 * Created by dragon on 16/3/28.
 */
public class DebugListener {
  public static AgentBuilder.Listener getListener() {
    return new AgentBuilder.Listener() {
      @Override
      public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader,
          DynamicType dynamicType) {

      }

      @Override
      public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader) {

      }

      @Override
      public void onError(String s, ClassLoader classLoader, Throwable throwable) {
        System.err.println("onError:" + s);
        throwable.printStackTrace();
      }

      @Override
      public void onComplete(String s, ClassLoader classLoader) {

      }
    };
  }


}
