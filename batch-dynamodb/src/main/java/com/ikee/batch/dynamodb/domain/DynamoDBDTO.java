package com.ikee.batch.dynamodb.domain;

public class DynamoDBDTO {
  private final String hashKey;
  private final String rangeStart;
  private String rangeEnd;
  
  public DynamoDBDTO(String hashKey, String rangeStart) {
    this.hashKey = hashKey;
    this.rangeStart = rangeStart;
  }
  
  public DynamoDBDTO(String hashKey, String rangeStart, String rangeEnd) {
    this(hashKey, rangeStart);
    this.rangeEnd = rangeEnd;
  }

  public String getRangeEnd() {
    return rangeEnd;
  }

  public String getHashKey() {
    return hashKey;
  }

  public String getRangeStart() {
    return rangeStart;
  }
  
  
}
