package com.dragon.study.bytebuddy.thrift;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.pool.TypePool;

import java.lang.reflect.Modifier;

/**
 * Created by dragon on 16/4/1.
 */
public class ThriftTypeDescription {

  public static TypeDescription protocolDescription() {
    return new TypeDescription.Latent("org.apache.thrift.protocol.TProtocol",
        Modifier.PUBLIC | Modifier.ABSTRACT, TypeDescription.Generic.OBJECT, null);
  }

  private static TypeDescription frameBufferDescription() {
    return new TypeDescription.Latent("org.apache.thrift.server.AbstractNonblockingServer$FrameBuffer",
        Modifier.PUBLIC, TypeDescription.Generic.OBJECT, null);
  }


  public static TypeDescription asyncFrameBufferDescription() {
    return new TypeDescription.Latent("org.apache.thrift.server.AbstractNonblockingServer$AsyncFrameBuffer",
        Modifier.PUBLIC, frameBufferDescription().asGenericType(), null);
  }


  public static TypeDescription boleanDescription() {
    return new TypeDescription.Latent(new TypeDescription.ForLoadedType(boolean.class).getName(),
        Modifier.PUBLIC | Modifier.FINAL, null, null);
  }
}
