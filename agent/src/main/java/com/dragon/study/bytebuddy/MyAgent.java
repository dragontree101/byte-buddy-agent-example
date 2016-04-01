package com.dragon.study.bytebuddy;

import com.dragon.study.bytebuddy.httpclient.HttpClientTransformer;
import com.dragon.study.bytebuddy.redis.RedisTransformer;

import net.bytebuddy.agent.builder.AgentBuilder;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.jar.JarFile;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * Created by dragon on 16/3/29.
 */
public class MyAgent {

  public static void premain(String arg, Instrumentation instrumentation) {

    URL[] urls = loadMyAgentCoreLib();

    String httpInterceptor = "com.dragon.study.bytebuddy.httpclient.HttpClientInterceptor";
    String redisInterceptor = "com.dragon.study.bytebuddy.redis.RedisInterceptor";

    new AgentBuilder.Default()
            .with(DebugListener.getListener())
            .type(nameStartsWith("org.apache.http").and(not(isInterface())))
            .transform(new HttpClientTransformer(httpInterceptor, urls))
            .type(nameStartsWith("redis.clients.jedis").and(not(isInterface())).and(not(isStatic())))
            .transform(new RedisTransformer(redisInterceptor, urls))
            .installOn(instrumentation);

  }


  private static URL[] loadMyAgentCoreLib() {
    // find myagent.jar
    final ClassPathResolver classPathResolver = new ClassPathResolver();
    boolean agentJarNotFound = classPathResolver.findAgentJar();

    if (!agentJarNotFound) {
      System.out.println("myagent-x.x.x(-SNAPSHOT).jar Fnot found.");
      return null;
    }

    final String myAgentJar = classPathResolver.getMyAgentJar();

    JarFile myAgentJarFile = getBootStrapJarFile(myAgentJar);
    if(myAgentJarFile == null) {
      System.out.println("myagent-x.x.x(-SNAPSHOT).jar Fnot found.");
      return null;
    }

    System.out.println("load myagent-x.x.x(-SNAPSHOT).jar :" + myAgentJarFile);

    URL[] urls = classPathResolver.resolvePlugins();
    return urls;
  }


  private static JarFile getBootStrapJarFile(String bootStrapCoreJar) {
    try {
      return new JarFile(bootStrapCoreJar);
    } catch (IOException ioe) {
      System.out.println(bootStrapCoreJar + " file not found.");
      ioe.printStackTrace();
      return null;
    }
  }
}
