package com.dragon.study.bytebuddy.rest;

import com.dragon.study.bytebuddy.annotation.Count;
import com.dragon.study.bytebuddy.annotation.EnableMetrics;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by dragon on 16/4/18.
 */
@Path("/")
@Component
@EnableMetrics
public class RestTest {

  @Path("/http-jersey")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String testHttpServlet() {
    System.out.println("begin test http jersey");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("end test http jersey");
    return "OK";
  }

  @Path("/http-rest/{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Count(name = "test.count")
  public String testHttpRest(@PathParam("id") int id) {
    System.out.println("begin test http rest, id is " + id);
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("end test http rest, id is " + id);
    return "OK";
  }
}
