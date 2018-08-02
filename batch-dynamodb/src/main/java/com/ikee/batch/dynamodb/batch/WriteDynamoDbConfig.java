package com.ikee.batch.dynamodb.batch;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.ikee.batch.dynamodb.infrastructure.CreateBuyRecordProcessor;
import com.ikee.batch.dynamodb.infrastructure.DynamoDbWriter;

/**
 * 
 *
 * @author hujianfeng
 * @date 2018/08/01
 *
 */
//@Configuration
public class WriteDynamoDbConfig {

  @Autowired
  StepBuilderFactory stepBuilderFactory;

  @Autowired
  JobBuilderFactory jobBuilderFactory;

  public IteratorItemReader<String> reader() {
    
    return new IteratorItemReader<>(new Iterator<String>() {
      int cur = 0;
      int total = 1000;
      
      @Override
      public boolean hasNext() {
        return cur < total;
      }

      @Override
      public String next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        cur++;
        return UUID.randomUUID().toString();
      }
    });
  }

  public CreateBuyRecordProcessor processor() {
    return new CreateBuyRecordProcessor();
  }

  public DynamoDbWriter writer() {
    return new DynamoDbWriter(dynamodb(), "buyRecord");
  }
  
  public FlatFileItemWriter<String> logWriter() {
    return new FlatFileItemWriterBuilder<String>()
      .name("uploadLog")
      .resource(new PathResource(Paths.get(".", "sectionItems-uploaded.json")))
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
    return jobBuilderFactory.get("writeJob")
      .incrementer(new RunIdIncrementer())
      .flow(step1())
      .end()
      .build();
  }

  @Bean
  public Step step1() {
    CompositeItemWriter<String> writer = new CompositeItemWriter<>();
    writer.setDelegates(Arrays.asList(writer(), logWriter()));
    return stepBuilderFactory.get("write1")
      .<String, String>chunk(1)
      .reader(reader())
      .processor(processor())
      .writer(writer)
      .build();
  }
}
