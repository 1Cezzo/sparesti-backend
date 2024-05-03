package edu.ntnu.idi.stud.team10.sparesti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Configuration for CORS. */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  /**
   * Add CORS mappings.
   *
   * @param registry CORS registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("*");
  }
}
