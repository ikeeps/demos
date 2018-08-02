package com.ikee.batch.dynamodb.domain;

public class Price {
  private Integer count;
  private String unit;

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  @Override
  public String toString() {
    return "Price [count=" + count + ", unit=" + unit + "]";
  }

}
