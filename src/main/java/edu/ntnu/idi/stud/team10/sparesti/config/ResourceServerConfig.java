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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

/** Configuration class for the resource server. */
@Configuration
@EnableWebSecurity
public class ResourceServerConfig {
  private final JwtDecoder jwtDecoder;
  private final HttpSessionRequestCache requestCache;
  private final SessionRegistry sessionRegistry;
  private final String frontendUrl;

  /**
   * Constructor for the ResourceServerConfig.
   *
   * @param jwtDecoder
   * @param requestCache
   * @param sessionRegistry
   * @param env
   */
  @Autowired
  public ResourceServerConfig(
      JwtDecoder jwtDecoder,
      HttpSessionRequestCache requestCache,
      SessionRegistry sessionRegistry,
      Environment env) {
    this.jwtDecoder = jwtDecoder;
    this.requestCache = requestCache;
    this.sessionRegistry = sessionRegistry;
    this.frontendUrl = env.getProperty("frontend.url");
  }

  @Bean
  @Order(2)
  /** Configures the security filter chain for the API. */
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers("/api/sendEmail")
                    .permitAll()
                    .requestMatchers("/api/users/create")
                    .permitAll()
                    .requestMatchers("/api/password-reset/reset-password")
                    .permitAll()
                    .requestMatchers("/login.html")
                    .permitAll()
                    .requestMatchers("/resetpassword.html")
                    .permitAll()
                    .requestMatchers("/stylesheet.css")
                    .permitAll()
                    .requestMatchers("/script.js")
                    .permitAll()
                    .requestMatchers("/images/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(
            custom ->
                custom
                    .loginPage("/login.html")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl(frontendUrl, true) // Redirect to frontendUrl
                    .successHandler(
                        (request, response, authentication) -> {
                          // Your custom success handling logic, if needed
                        }))
        .oauth2ResourceServer(
            oauth2ResourceServer ->
                oauth2ResourceServer.jwt(
                    jwt ->
                        jwt.decoder(jwtDecoder)
                            .jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  /** Configures the JwtAuthenticationConverter. */
  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    grantedAuthoritiesConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

    return jwtConverter;
  }
}
