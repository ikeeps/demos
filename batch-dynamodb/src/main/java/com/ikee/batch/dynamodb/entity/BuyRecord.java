package com.ikee.batch.dynamodb.entity;

/**
 * 
 * <a href="https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-time-series.html">time series data</a>
 * @author hujianfeng
 * @date 2018/08/02
 *
 */
public class BuyRecord {
  private String date;
  private String time;
  private String product;
  private String userId;
  private int count;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

}
