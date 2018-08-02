package com.ikee.batch.dynamodb.infrastructure;

import org.springframework.batch.item.ItemProcessor;

public class DynamoDbWriteProcessor implements ItemProcessor<String, String> {

  @Override
  public String process(String item) throws Exception {
    return item;
  }

}
