package com.dragon.study.bytebuddy.thrift;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.AbstractNonblockingServer;

import java.util.concurrent.Callable;

/**
 * Created by dragon on 16/3/29.
 */
public class ThriftServerInterceptor {

  public static Boolean process(
      @SuperCall
      Callable<Boolean> client,
      @AllArguments
      Object[] args) throws Exception {

    try {
      if(args.length == 1) {
        System.out.println("args length is 1, so TAsyncBaseProcessor");
        if(args[0] instanceof AbstractNonblockingServer.AsyncFrameBuffer) {
          AbstractNonblockingServer.AsyncFrameBuffer fb = (AbstractNonblockingServer.AsyncFrameBuffer)args[0];
          final TProtocol in = fb.getInputProtocol();
          if(in instanceof TBinaryProtocol) {
            String methodName = getMethodName((TBinaryProtocol)in);
            System.out.println("async method name is " + methodName);
          }
        }
      } else if (args.length == 2) {
        System.out.println("args length is 2, so TBaseProcessor");
        if(args[0] instanceof TBinaryProtocol && args[1] instanceof TBinaryProtocol) {
          TBinaryProtocol in = (TBinaryProtocol)args[0];
          String methodName = getMethodName(in);
          System.out.println("base method name is " + methodName);
        }
      }
      return client.call();
    } catch (Exception e) {
      throw e;
    }
  }

  private static String getMethodName(TBinaryProtocol in) throws Exception {
    TMessage message = in.readMessageBegin();
    int readedBuffer = in.getTransport().getBufferPosition();
    in.getTransport().consumeBuffer(-1 * readedBuffer);
    return message.name;
  }
}
