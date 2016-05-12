package com.dragon.study.bytebuddy.mysql;

import com.dragon.study.bytebuddy.Trace;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextRefreshedHolder;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;

/**
 * Created by dragon on 16/3/29.
 */
public class MysqlInterceptor {

  public static ResultSetInternalMethods sqlQueryDirect(
      @This
      Object zuper,
      @SuperCall
      Callable<ResultSetInternalMethods> client,
      @AllArguments
      Object[] args) throws Exception {

    Person person = ApplicationContextRefreshedHolder.getBean(Person.class);
    Trace trace = new Trace();
    long start = System.currentTimeMillis();
    Statement statement = (Statement) args[0];
    if (statement instanceof PreparedStatement) {
      try {
        String sql = ((PreparedStatement) statement).asSql();
        InetSocketAddress inetSocketAddress = (InetSocketAddress)((MysqlIO)zuper).mysqlConnection.getRemoteSocketAddress();
        int ipv4 = ByteBuffer.wrap(inetSocketAddress.getAddress().getAddress()).getInt();
        int port = inetSocketAddress.getPort();
        System.out.println("ipv4 is " + ipv4 + ", port is " + port);
        ResultSetInternalMethods response = client.call();
        trace.setCost(System.currentTimeMillis() - start);
        trace.setStatusCode(200);
        System.out.println("sql is " + sql + ", person is " + person.toString());
        return response;
      } catch (Exception e) {
        trace.setCost(System.currentTimeMillis() - start);
        trace.setE(e);
        trace.setStatusCode(-1);
        throw e;
      }
    } else {
      try {
        ResultSetInternalMethods response = client.call();
        return response;
      } catch (Exception e) {
        throw e;
      }
    }
  }
}
