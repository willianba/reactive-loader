package com.tcc.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class LoaderApplication {

  public static void main(String[] args) {
    SpringApplication.run(LoaderApplication.class, args);
  }
}
