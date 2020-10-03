package com.tcc.loader.helper;

import java.util.Base64;

import com.tcc.loader.dto.GitHubFile;
import com.tcc.loader.dto.TranslatedFile;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class Converter {

  public Mono<GitHubFile> convertToGitHubFile(Mono<TranslatedFile> fileMono) {
    return fileMono.flatMap(file -> {
      String base64Content = Base64.getEncoder().encodeToString(file.getContent().getBytes());

      GitHubFile githubFile = new GitHubFile();
      githubFile.setFileName(file.getFileName());
      githubFile.setContent(base64Content);
      githubFile.setMessage("Create translated file"); // TODO remove hardcoded
      return Mono.just(githubFile);
    });
  }
}
