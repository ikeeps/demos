package com.ikee.batch.dynamodb.infrastructure;

import java.time.LocalTime;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.batch.item.ItemProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikee.batch.dynamodb.entity.BuyRecord;

/**
 * 
 *
 * @author hujianfeng
 * @date 2018/08/02
 *
 */
public class CreateBuyRecordProcessor implements ItemProcessor<String, String> {
  
  private ObjectMapper mapper = new ObjectMapper();
  
  @Override
  public String process(String item) throws Exception {
    BuyRecord record = new BuyRecord();
    record.setCount(RandomUtils.nextInt(1, 9));
    record.setDate("2018-08-02");
    record.setTime(randomTime());
    record.setProduct(Integer.toString(RandomUtils.nextInt(1, 100)));
    record.setUserId(item);
    return mapper.writeValueAsString(record);
  }

  private String randomTime() {
    LocalTime now = LocalTime.now();
    LocalTime time = now.minusSeconds(RandomUtils.nextInt(1, 36000));
    return time.toString();
  }

}
