package com.tcc.loader.client;

import java.time.LocalDate;

import com.tcc.loader.dto.GitHubFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Component
public class GitHubClient {

  private static final Logger logger = LoggerFactory.getLogger(GitHubClient.class);
  private static final String GITHUB_JSON = "application/vnd.github.v3+json";

  private WebClient client;

  @Value("${github.personal.token}")
  private String gitPersonalToken;

  public GitHubClient() {
    this.client = WebClient.builder()
      .baseUrl("https://api.github.com/repos/")
      .build();
  }

  public void uploadFile(Mono<GitHubFile> fileMono) {
    fileMono.subscribe(file -> {
      String repoUrl = getDirectoryApiUrl(file.getFileName());
      client.put()
        .uri(repoUrl)
        .header(HttpHeaders.ACCEPT, GITHUB_JSON)
        .header(HttpHeaders.AUTHORIZATION, "token " + gitPersonalToken)
        .body(fileMono, GitHubFile.class)
        .retrieve()
        .bodyToMono(Void.class)
        .doOnSuccess(success -> logger.info("File {} uploaded", file.getFileName()))
        .doOnError(WebClientResponseException.class, error -> logger.error("Error uploading file {}: {}", file.getFileName(), error.getMessage()))
        .subscribe();
      });
  }

  private String getDirectoryApiUrl(String fileName) {
    StringBuilder sb = new StringBuilder();
    return sb.append("willianba") // TODO remove hadcoded user
      .append("/")
      .append("translated-files") // TODO remove harcoded repo
      .append("/contents/reactive/")
      .append(LocalDate.now())
      .append("/")
      .append(fileName)
      .toString();
  }
}
