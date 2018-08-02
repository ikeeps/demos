package com.ikee.batch.dynamodb.infrastructure;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.ikee.batch.dynamodb.domain.DynamoDBDTO;

/**
 * 
 * TrendCsvQueryMapper
 * 
 * @author hujianfeng
 * @date 2018/07/09
 *
 */
public class BuyRecordReader extends DynamoDbReader {
  private static final String TABLE_NAME = "buyRecord";
  private static final String HASH_KEY = "date";
  private static final String RANGE_KEY = "time";

  private final DynamoDB dynamodb;
  private final DynamoDBDTO queryDTO;

  public BuyRecordReader(DynamoDB dynamodb, DynamoDBDTO query, Integer rate) {
    super(rate);
    if (dynamodb == null) {
      throw new IllegalArgumentException("dynamodb is required");
    }
    if (query == null) {
      throw new IllegalArgumentException("query is required");
    }
    this.dynamodb = dynamodb;
    this.queryDTO = query;
  }
  
  QuerySpec buildQuerySpec(DynamoDBDTO queryDTO, int capacity) {
    QuerySpec querySpec = new QuerySpec()
            .withHashKey(HASH_KEY, queryDTO.getHashKey());

    RangeKeyCondition range;
    if (queryDTO.getRangeEnd() != null) {
      range = new RangeKeyCondition(RANGE_KEY).between(queryDTO.getRangeStart(), queryDTO.getRangeEnd());
    } else {
      range = new RangeKeyCondition(RANGE_KEY).ge(queryDTO.getRangeStart());
    }
    querySpec.withRangeKeyCondition(range);
    // limit optimize
    // read 8 item cost 0.5 capacity ?
    querySpec.setMaxPageSize(capacity * 2 * 8);
    querySpec.withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);
//    querySpec.withSelect(Select.ALL_PROJECTED_ATTRIBUTES);

    return querySpec;
  }

  
  
  @Override
  protected Item nextItem() {
    
    return super.nextItem();
  }

  @Override
  protected ItemCollection<QueryOutcome> buildQuery() {
    QuerySpec buildQuerySpec = buildQuerySpec(queryDTO, rate);
    Table table = dynamodb.getTable(TABLE_NAME);
    return table.query(buildQuerySpec);
  }

}
