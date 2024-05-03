package edu.ntnu.idi.stud.team10.sparesti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** Configuration for the application. */
@Configuration
public class AppConfig {

  /**
   * Configure the RestTemplate. This is required to use chatGPT API.
   *
   * @return RestTemplate
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
