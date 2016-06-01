package com.dragon.study.bytebuddy.jersey;

import net.bytebuddy.description.type.TypeDescription;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by dragon on 16/4/1.
 */
public class JerseyDispatcherDescription {

  public static TypeDescription containerRequestDescription() {
    return new TypeDescription.Latent("org.glassfish.jersey.server.ContainerRequest",
        Modifier.PUBLIC, inboundMessageContextDescription().asGenericType(), Arrays
        .asList(containerRequestContextDescription().asGenericType(),
            requestDescription().asGenericType(), httpHeadersDescription().asGenericType(),
            propertiesDelegateDescription().asGenericType()));
  }

  private static TypeDescription inboundMessageContextDescription() {
    return new TypeDescription.Latent("org.glassfish.jersey.message.internal.InboundMessageContext",
        Modifier.PUBLIC | Modifier.ABSTRACT, TypeDescription.Generic.OBJECT, null);
  }

  public static TypeDescription containerRequestContextDescription() {
    return new TypeDescription.Latent("javax.ws.rs.container.ContainerRequestContext",
        Modifier.PUBLIC | Modifier.INTERFACE, TypeDescription.Generic.OBJECT, null);
  }

  private static TypeDescription requestDescription() {
    return new TypeDescription.Latent("javax.ws.rs.core.Request",
        Modifier.PUBLIC | Modifier.INTERFACE, TypeDescription.Generic.OBJECT, null);
  }

  private static TypeDescription httpHeadersDescription() {
    return new TypeDescription.Latent("javax.ws.rs.core.HttpHeaders",
        Modifier.PUBLIC | Modifier.INTERFACE, TypeDescription.Generic.OBJECT, null);
  }

  private static TypeDescription propertiesDelegateDescription() {
    return new TypeDescription.Latent("org.glassfish.jersey.internal.PropertiesDelegate",
        Modifier.PUBLIC | Modifier.INTERFACE, TypeDescription.Generic.OBJECT, null);
  }
}
