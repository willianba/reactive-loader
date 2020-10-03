package com.tcc.loader.rabbitmq.subscriber;

import com.tcc.loader.dto.TranslatedFile;
import com.tcc.loader.service.LoadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class GitHubContentSubscriber {

  private static final Logger logger = LoggerFactory.getLogger(GitHubContentSubscriber.class);

  @Autowired
  private LoadService service;

  @RabbitListener(queues = "${translated.queue}")
  public void receiveTranslatedFiles(TranslatedFile file) {
    logger.info("Received file {} to be uploaded", file.getFileName());
    service.uploadFilesToGitHub(Mono.just(file));
  }
}
