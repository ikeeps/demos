package com.ikee.batch.dynamodb.entity;

import com.ikee.batch.dynamodb.domain.Period;

public class Product extends EntityBase {

  private String name;
  private Period period;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Period getPeriod() {
    return period;
  }

  public void setPeriod(Period period) {
    this.period = period;
  }

  @Override
  public String toString() {
    return "Product [name=" + name + ", period=" + period + "]";
  }

}
