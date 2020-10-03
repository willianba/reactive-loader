package com.tcc.loader.service;

import com.tcc.loader.client.GitHubClient;
import com.tcc.loader.dto.GitHubFile;
import com.tcc.loader.dto.TranslatedFile;
import com.tcc.loader.helper.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class LoadService {

  @Autowired
  private GitHubClient client;

  @Autowired
  private Converter converter;

  public void uploadFilesToGitHub(Mono<TranslatedFile> file) {
    Mono<GitHubFile> githubFile = converter.convertToGitHubFile(file);
    client.uploadFile(githubFile);
  }
}
