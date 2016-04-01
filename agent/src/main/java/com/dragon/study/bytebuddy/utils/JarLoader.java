package com.dragon.study.bytebuddy.utils;

import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * Created by dragon on 16/4/1.
 */
public class JarLoader {

  public static URL[] loadMyAgentCoreLib() {
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
