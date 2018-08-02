package com.ikee.batch.dynamodb.infrastructure;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class CreateSectionJsonListener extends JobExecutionListenerSupport {
  private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void beforeJob(JobExecution jobExecution) {
    Path path = Paths.get(".", "sectionItems.json");
    try {
      Files.delete(path);
    } catch (IOException e) {
      log.error("clear file error", e);
    }

  }

}
