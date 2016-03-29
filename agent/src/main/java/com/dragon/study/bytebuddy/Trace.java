package com.dragon.study.bytebuddy;

/**
 * Created by dragon on 16/3/28.
 */
public class Trace {
  private String url;
  private int statusCode;
  private long cost;
  private Exception e;

  public Trace() {
  }

  public Trace(String url, int statusCode, long cost, Exception e) {
    this.url = url;
    this.statusCode = statusCode;
    this.cost = cost;
    this.e = e;
  }

  public Trace(String url, int statusCode, long cost) {
    this(url, statusCode, cost, null);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public long getCost() {
    return cost;
  }

  public void setCost(long cost) {
    this.cost = cost;
  }

  public Exception getE() {
    return e;
  }

  public void setE(Exception e) {
    this.e = e;
  }

  @Override
  public String toString() {
    return "Trace{" +
        "url='" + url + '\'' +
        ", statusCode=" + statusCode +
        ", cost=" + cost +
        ", e=" + e +
        '}';
  }
}
