package com.dragon.study.bytebuddy.exception;

/**
 * Created by dragon on 16/4/1.
 */
public class LoadMyAgentException extends RuntimeException {
  public LoadMyAgentException(String message, Throwable cause) {
    super(message, cause);
  }

  public LoadMyAgentException(String message) {
    super(message);
  }

}
