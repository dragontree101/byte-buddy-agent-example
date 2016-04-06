package com.dragon.study.bytebuddy.timer;

import com.dragon.study.bytebuddy.bean.Person;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpEngine;
import redis.clients.jedis.Jedis;

/**
 * Created by dragon on 16/3/28.
 */
@Component
public class TimerPerson {

  @Autowired
  private Person person;

  @Scheduled(fixedDelay = 5000L, initialDelay = 1000L)
  public void httpClientTest() {
//    System.out.println(person.toString() + " calling http client, time is " + System.currentTimeMillis());
    OkHttpClient client = new OkHttpClient();
    String response;
    try {
      response = getResponse(client, "http://enjoy.ricebook.com/");
    } catch (IOException e) {
      e.printStackTrace();
      response = "request error";
    }

    System.out.println("OK");

  }

  private String getResponse(OkHttpClient client, String url) throws IOException {
    Request request = new Request.Builder()
        .url("http://enjoy.ricebook.com/")
        .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  @Scheduled(fixedDelay = 10000L, initialDelay = 3000L)
  public void redisTest() {
    System.out.println(person.toString() + " calling redis, time is " + System.currentTimeMillis());
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.set("a", "b");
    String value = jedis.get("a");
    String info = jedis.info();
    System.out.println("key is a, value is " + value + ", info is " + info);
    jedis.close();

  }

  public static void main(String[] args) {
    TimerPerson person = new TimerPerson();
    person.httpClientTest();
  }
}
