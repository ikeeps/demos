package com.ikee.batch.dynamodb.infrastructure;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

public class DynamoDbWriter implements ItemWriter<String> {
  private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final int MAX_RETRIES = 5;
  DynamoDB dynamodb;
  String tableName;

  public DynamoDbWriter(DynamoDB dynamodb, String tableName) {
    this.dynamodb = dynamodb;
    this.tableName = tableName;
  }

  @Override
  public void write(List<? extends String> items) throws Exception {
    TableWriteItems itemsWrite = new TableWriteItems(tableName);
    List<Item> itemToPut = new ArrayList<>(items.size());
    for (String json : items) {
      itemToPut.add(Item.fromJSON(json));
    }
    itemsWrite.withItemsToPut(itemToPut);
    BatchWriteItemOutcome outcome = dynamodb.batchWriteItem(itemsWrite);
    logCapacity(outcome);

    int attempts = 0;
    while (!outcome.getUnprocessedItems().isEmpty() && attempts < MAX_RETRIES) {
      Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
      // Check for unprocessed keys which could happen if you exceed provisioned
      // throughput
      if (attempts > 0) {
        // exponential backoff
        try {
          Thread.sleep((1 << attempts) * 1000L);
        } catch (InterruptedException ignored) {
          Thread.currentThread().interrupt();
        }
      }
      attempts++;
      outcome = dynamodb.batchWriteItemUnprocessed(unprocessedItems);
      log.info("Retrieving the unprocessed items. retry times [ + {} + ]", attempts);
    }

    Thread.sleep(1000);
  }

  private void logCapacity(BatchWriteItemOutcome batchWriteItem) {
    if (batchWriteItem == null || batchWriteItem.getBatchWriteItemResult() == null) {
      return;
    }
    Double total = 0.0;
    if (batchWriteItem.getBatchWriteItemResult().getConsumedCapacity() != null) {
      for (ConsumedCapacity capacity : batchWriteItem.getBatchWriteItemResult().getConsumedCapacity()) {
        total += capacity.getCapacityUnits();
      }
    }
    log.info("consumed capacity : {} ", total);
    Map<String, List<WriteRequest>> unprocessedItems = batchWriteItem.getUnprocessedItems();
    log.info("unprocessed: {}", unprocessedItems.size());
  }

}
