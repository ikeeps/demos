package com.ikee.batch.dynamodb.domain;

public class Period {
  private Long start;
  private Long end;

  public Long getStart() {
    return start;
  }

  public void setStart(Long start) {
    this.start = start;
  }

  public Long getEnd() {
    return end;
  }

  public void setEnd(Long end) {
    this.end = end;
  }

  @Override
  public String toString() {
    return "Period [start=" + start + ", end=" + end + "]";
  }

}
