package com.ikee.batch.dynamodb.infrastructure;

import org.springframework.batch.item.ItemProcessor;

import com.amazonaws.services.dynamodbv2.document.Item;

public class DynamoDbReadProcessor implements ItemProcessor<Item, String> {

  @Override
  public String process(Item item) throws Exception {
    return item.toJSON();
  }

}
