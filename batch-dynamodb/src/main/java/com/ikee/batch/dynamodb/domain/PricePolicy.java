package com.ikee.batch.dynamodb.domain;

import com.ikee.batch.dynamodb.entity.Product;

/**
 * 
 *
 * @author hujianfeng
 * @date 2018/08/02
 *
 */
public interface PricePolicy {

  Price getPrice(Product product);
}
