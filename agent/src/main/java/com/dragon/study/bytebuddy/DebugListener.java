package com.dragon.study.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * Created by dragon on 16/3/28.
 */
public class DebugListener {
  public static AgentBuilder.Listener getListener() {
    return new AgentBuilder.Listener() {
      @Override
      public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader,
          JavaModule module, DynamicType dynamicType) {

      }

      @Override
      public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader,
          JavaModule module) {

      }

      @Override
      public void onError(String typeName, ClassLoader classLoader, JavaModule module,
          Throwable throwable) {
        System.err.println("onError:" + typeName);
        throwable.printStackTrace();
      }

      @Override
      public void onComplete(String typeName, ClassLoader classLoader, JavaModule module) {

      }

    };
  }


}
