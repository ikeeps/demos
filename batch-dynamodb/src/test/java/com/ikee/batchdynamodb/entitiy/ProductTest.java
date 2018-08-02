package com.ikee.batchdynamodb.entitiy;

import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikee.batch.dynamodb.domain.Period;
import com.ikee.batch.dynamodb.entity.Product;

public class ProductTest {

  @Test
  public void toJson() throws JsonProcessingException {
    Product product = new Product();
    product.setId(UUID.randomUUID().toString());
    product.setName("coke-x");

    Period period = new Period();
    period.setStart(System.currentTimeMillis());
    product.setPeriod(period);

    ObjectMapper mapper = new ObjectMapper();
    String productJson = mapper.writeValueAsString(product);

    System.out.println(productJson);
  }
}
