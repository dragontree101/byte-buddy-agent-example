package com.dragon.study.bytebuddy.jersey;

import net.bytebuddy.asm.Advice;

import org.glassfish.jersey.server.ContainerRequest;

/**
 * Created by dragon on 16/5/4.
 */
public class RequestFilterAdviceInterceptor {

  @Advice.OnMethodEnter
  static void enter(
      @Advice.This
      Object thiz,
      @Advice.Argument(value = 0)
      Object arg1) {

    ContainerRequest request = (ContainerRequest) arg1;
//    String path = request.getUriInfo().getMatchedModelResource().getPath();

    System.out.println(thiz.getClass().getCanonicalName());
//    System.out.println(request.getUriInfo().getMatchedModelResource().getPath());
    System.out.println("=======filter enter========");
  }

  @Advice.OnMethodExit(onThrowable = Throwable.class)
  static void exit(
      @Advice.Thrown
      Throwable t) {
    System.out.println("=======filter exit========");
  }


}
