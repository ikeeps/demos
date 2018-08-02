package com.ikee.batch.dynamodb.infrastructure;

import java.util.Iterator;

import org.springframework.batch.item.ItemReader;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Page;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.google.common.util.concurrent.RateLimiter;

public abstract class DynamoDbReader implements ItemReader<Item> {
  protected ItemCollection<QueryOutcome> query;
  protected Page<Item, QueryOutcome> currentPage;
  protected Iterator<Item> currentItem;
  protected Integer rate;
  protected RateLimiter limiter;

  public DynamoDbReader(Integer rate) {
    this.rate = rate;
    this.limiter = RateLimiter.create(rate);
  }
  
  protected Item nextItem() {
    if (query == null) {
      query = buildQuery();
    }
    if (currentPage == null) {
      currentPage = query.firstPage();
    }

    if (currentItem == null) {
      currentItem = currentPage.iterator();
      limitRate();
    }

    if (currentItem.hasNext()) {
      return currentItem.next();
    } else {
      if (currentPage.hasNextPage()) {
        currentPage = currentPage.nextPage();
        currentItem = currentPage.iterator();
        limitRate();
        return currentItem.next();
      } else {
        return null;
      }
    }
  }
  
  private void limitRate() {
    QueryOutcome lowLevelResult = currentPage.getLowLevelResult();
    if (lowLevelResult == null) {
      return;
    }
    QueryResult queryResult = lowLevelResult.getQueryResult();
    if (queryResult == null) {
      return;
    }
    ConsumedCapacity consumedCapacity = queryResult.getConsumedCapacity();
    if (consumedCapacity == null) {
      return;
    }
    int capacityUnits = (int) (consumedCapacity.getCapacityUnits() + 1);
    limiter.acquire(capacityUnits);
  }

  @Override
  public Item read() throws Exception {
    return nextItem();
  }

  protected abstract ItemCollection<QueryOutcome> buildQuery();
}
