package com.dragon.study.bytebuddy.jersey;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import org.glassfish.jersey.server.ContainerRequest;

import java.util.concurrent.Callable;

import javax.ws.rs.core.Response;

/**
 * Created by dragon on 16/3/29.
 */
public class JerseyDispatcherInterceptor {

  public static Response dispatcher(
      @SuperCall
      Callable<Response> client,
      @AllArguments
      Object[] args) throws Exception {

    ContainerRequest containerRequest = (ContainerRequest) args[1];
    System.out.println(containerRequest.getMethod());
    System.out.println(containerRequest.getUriInfo().getMatchedModelResource().getPath());
    Response response = client.call();
    return response;


  }


}
