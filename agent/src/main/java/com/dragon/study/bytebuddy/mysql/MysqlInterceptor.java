package com.dragon.study.bytebuddy.mysql;

import com.dragon.study.bytebuddy.Trace;
import com.dragon.study.bytebuddy.bean.Person;
import com.dragon.study.bytebuddy.context.ApplicationContextHolder;
import com.mysql.jdbc.MysqlIO;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.concurrent.Callable;

import redis.clients.jedis.Connection;

/**
 * Created by dragon on 16/3/29.
 */
public class MysqlInterceptor {

  public static ResultSetInternalMethods sqlQueryDirect(
      @SuperCall
      Callable<ResultSetInternalMethods> client,
      @This
      Object thiz,
      @AllArguments
      Object[] args) throws Exception {

    Person person = ApplicationContextHolder.getBean(Person.class);
    Trace trace = new Trace();
    long start = System.currentTimeMillis();
    Statement statment = (Statement) args[0];
    if (statment instanceof PreparedStatement) {
      try {
        String sql = (String) args[1];
        MysqlIO mysqlIO = (MysqlIO) thiz;
        mysqlIO.mysqlConnection.getInetAddress();
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
