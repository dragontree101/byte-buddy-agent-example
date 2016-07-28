package com.dragon.study.bytebuddy.timer;

import com.dragon.study.bytebuddy.advice.AdviceProfiled;
import com.dragon.study.bytebuddy.annotation.EnableMetrics;
import com.dragon.study.bytebuddy.bean.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import redis.clients.jedis.Jedis;

/**
 * Created by dragon on 16/3/28.
 */
@Component
@EnableMetrics
public class TimerPerson {

  @Autowired
  private Person person;

  //  @Scheduled(fixedDelay = 5000L, initialDelay = 1000L)
  public void httpClientTest() {
    OkHttpClient client = new OkHttpClient();
    String response;
    try {
      response = getResponse(client, "http://enjoy.ricebook.com/");
    } catch (IOException e) {
      e.printStackTrace();
      response = "request error";
    }

    System.out.println(response);
  }

  private String getResponse(OkHttpClient client, String url) throws IOException {
    Request request = new Request.Builder().url("http://enjoy.ricebook.com/").build();

    Response response = client.newCall(request).execute();
    return String.valueOf(response.code());
  }

  //  @Scheduled(fixedDelay = 10000L, initialDelay = 3000L)
  public void redisTest() {
    System.out.println(person.toString() + " calling redis, time is " + System.currentTimeMillis());
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.set("a", "b");
    String value = jedis.get("a");
    System.out.println("key is a, value is " + value);
    jedis.info();
    jedis.close();
  }

//  @Count(name = "test.count")
//  @Scheduled(fixedDelay = 3000L, initialDelay = 1000L)
  public void testCount() {
    System.out.println("begin test count");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("end test count");
  }

  //  @Scheduled(fixedDelay = 7000L, initialDelay = 7000L)
  public void testMysql() {
    System.out.println("begin test mysql jdbc");
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "");
      PreparedStatement pp = conn.prepareStatement("select * from demo_table where age = ?");
      pp.setInt(1, 30);
      ResultSet re = pp.executeQuery();
      String result;
      if (re.next()) {
        result = "name is :" + re.getString(1) + ", age is :" + re.getInt(2) + ", score is :" + re
            .getInt(3);
        System.out.println("result is " + result);
      }
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("end test mysql jdbc");
  }

    @Scheduled(fixedDelay = 3000L, initialDelay = 3000L)
  public void testAdvice() {
    System.out.println("begin test advice");
    AdviceProfiled adviceProfiled = new AdviceProfiled();
    adviceProfiled.profile(1000, "test");
    System.out.println("end test advice");
  }


  public static void main(String[] args) {
    TimerPerson person = new TimerPerson();
    person.httpClientTest();
  }
}
