package com.ikee.batch.dynamodb;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 
 * BatchDynamodbApplication
 * 
 * @author hujianfeng
 * @date 2018/07/12
 *
 */

@SpringBootApplication
@EnableBatchProcessing
public class BatchDynamodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchDynamodbApplication.class, args);
	}
}
