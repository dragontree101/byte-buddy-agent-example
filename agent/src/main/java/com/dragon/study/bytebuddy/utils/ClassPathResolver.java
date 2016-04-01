package com.dragon.study.bytebuddy.utils;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClassPathResolver {

  private static final Pattern DEFAULT_AGENT_PATTERN = Pattern.compile("myagent(-[0-9]+\\.[0-9]+\\.[0-9]+(\\-SNAPSHOT)?)?\\.jar");

  private String classPath;
  private Pattern agentPattern;
  private String agentJarName;
  private String agentJarFullPath;
  private String agentDirPath;
  private String myAgentJar;

  public ClassPathResolver() {
    this(getClassPathFromSystemProperty());
  }

  public ClassPathResolver(String classPath) {
    this.classPath = classPath;
    this.agentPattern = DEFAULT_AGENT_PATTERN;
  }

  public boolean findAgentJar() {
    Matcher matcher = agentPattern.matcher(classPath);
    if (!matcher.find()) {
      return false;
    }

    this.agentJarName = parseAgentJar(matcher);
    this.agentJarFullPath = parseAgentJarPath(classPath, agentJarName);
    if (agentJarFullPath == null) {
      return false;
    }

    this.agentDirPath = parseAgentDirPath(agentJarFullPath);
    this.myAgentJar = findMyAgentJar();
    if (this.myAgentJar == null) {
      return false;
    }

    return true;
  }


  public URL[] resolvePlugins() {
    final File file = new File(getAgentPluginPath());
    if (!file.exists()) {
      System.out.println(file + " not found");
      return new URL[0];
    }

    if (!file.isDirectory()) {
      System.out.println(file + " is not a directory");
      return new URL[0];
    }

    final File[] jars = file.listFiles((dir, name) -> name.endsWith(".jar"));

    if (jars == null || jars.length == 0) {
      return new URL[0];
    }

    final URL[] urls = new URL[jars.length];

    for (int i = 0; i < jars.length; i++) {
      try {
        urls[i] = jars[i].toURI().toURL();
      } catch (MalformedURLException e) {
        throw new RuntimeException("Fail to load plugin jars", e);
      }
    }

    System.out.println("Found plugins: " + Arrays.deepToString(jars));

    return urls;
  }

  private String findMyAgentJar() {
    String bootDir = agentDirPath;
    File file = new File(bootDir);
    File[] files = file.listFiles((dir, name) -> {
      Matcher matcher = agentPattern.matcher(name);
      if (matcher.matches()) {
        System.out.println("found bootStrapCore. " + name);
        return true;
      }
      return false;
    });
    if (files == null || files.length == 0) {
      System.out.println("bootStrapCore not found.");
      return null;
    } else if (files.length == 1) {
      return files[0].getAbsolutePath();
    } else {
      System.out.println("too many bootStrapCore found. " + Arrays.toString(files));
      return null;
    }
  }

  private String parseAgentJarPath(String classPath, String agentJar) {
    String[] classPathList = classPath.split(File.pathSeparator);
    for (String findPath : classPathList) {
      boolean find = findPath.contains(agentJar);
      if (find) {
        return findPath;
      }
    }
    return null;
  }

  private String parseAgentJar(Matcher matcher) {
    int start = matcher.start();
    int end = matcher.end();
    return this.classPath.substring(start, end);
  }

  public String parseAgentDirPath(String agentJarFullPath) {
    int index1 = agentJarFullPath.lastIndexOf("/");
    int index2 = agentJarFullPath.lastIndexOf("\\");
    int max = Math.max(index1, index2);
    if (max == -1) {
      return null;
    }
    return agentJarFullPath.substring(0, max);
  }

  public static String getClassPathFromSystemProperty() {
    String classPath = System.getProperty("java.class.path");
    System.out.println("class path is " + classPath);
    return System.getProperty("java.class.path");
  }


  public String getMyAgentJar() {
    return this.myAgentJar;
  }

  public String getAgentPluginPath() {
    return this.agentDirPath;
  }

}
