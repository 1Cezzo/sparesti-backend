package edu.ntnu.idi.stud.team10.sparesti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Configuration class for security. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final String frontendUrl;

  /**
   * Constructor for the SecurityConfig.
   *
   * @param env The environment
   */
  @Autowired
  public SecurityConfig(Environment env) {
    this.frontendUrl = env.getProperty("frontend.url");
  }

  @Bean
  @Order(3)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .requiresChannel(requiresChannel -> requiresChannel.anyRequest().requiresSecure())
        .formLogin(Customizer.withDefaults());

    // Add configuration to ignore certain URLs
    http.authorizeRequests(
            authorize ->
                authorize
                    .antMatchers("/favicon.ico", "/resources/**", "/error")
                    .permitAll()
                    // Add more antMatchers if needed
                    .anyRequest()
                    .authenticated())
        .formLogin(Customizer.withDefaults());

    return http.build();
  }

  /**
   * Configures the password encoder.
   *
   * @return (BCryptPasswordEncoder) The password encoder
   */
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the CORS settings.
   *
   * @return (WebMvcConfigurer) The CORS settings
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(frontendUrl)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
            .allowedHeaders("*");
      }
    };
  }

  /**
   * Configures the session registry.
   *
   * @return (SessionRegistry) The session registry
   */
  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  /**
   * Configures the HttpSessionEventPublisher.
   *
   * @return (HttpSessionEventPublisher) The HttpSessionEventPublisher
   */
  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  /**
   * Configures the request cache.
   *
   * @return (HttpSessionRequestCache) The request cache
   */
  @Bean
  public HttpSessionRequestCache requestCache() {
    return new HttpSessionRequestCache();
  }
}
