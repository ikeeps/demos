package com.ikee.batch.dynamodb.batch;

import java.nio.file.Paths;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.ikee.batch.dynamodb.domain.DynamoDBDTO;
import com.ikee.batch.dynamodb.infrastructure.DynamoDbReadProcessor;
import com.ikee.batch.dynamodb.infrastructure.BuyRecordReader;

/**
 * 
 *
 * @author hujianfeng
 * @date 2018/07/31
 *
 */
@Configuration
public class CsvConfiguration {

  @Autowired
  StepBuilderFactory stepBuilderFactory;

  @Autowired
  JobBuilderFactory jobBuilderFactory;

  public BuyRecordReader reader(DynamoDB dynamodb, DynamoDBDTO query) {
    return new BuyRecordReader(dynamodb, query, 5);
  }

  public DynamoDbReadProcessor processor() {
    return new DynamoDbReadProcessor();
  }

  public FlatFileItemWriter<String> writer() {
    return new FlatFileItemWriterBuilder<String>()
      .name("buyRecordWriter")
      .resource(new PathResource(Paths.get(".", "buyRecord.json")))
      .encoding("UTF-8")
      .lineAggregator(new PassThroughLineAggregator<>())
      .build();
  }

  @Bean
  public DynamoDB dynamodb() {
    AmazonDynamoDBClientBuilder standard = AmazonDynamoDBClientBuilder.standard();
    standard
      .setEndpointConfiguration(new EndpointConfiguration("http://192.168.99.3:8000", "http://192.168.99.3:8000"));
    return new DynamoDB(standard.build());
  }

  @Bean
  public Job trendCsvJob() {
    return jobBuilderFactory.get("readJob")
      .incrementer(new RunIdIncrementer())
      .flow(step1())
      .end()
      .build();
  }

  @Bean
  public Step step1() {
    DynamoDBDTO query = new DynamoDBDTO("2018-08-02", "10:30:00.001", "22:30:00.001");
    BuyRecordReader reader = reader(dynamodb(), query);
    return stepBuilderFactory.get("readDynamodb")
      .<Item, String>chunk(10)
      .reader(reader)
      .processor(processor())
      .writer(writer())
      .build();
  }
}
