package edu.ntnu.idi.stud.team10.sparesti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

  @Autowired
  public ResourceServerConfig(JwtDecoder jwtDecoder, HttpSessionRequestCache requestCache) {
    this.jwtDecoder = jwtDecoder;
    this.requestCache = requestCache;
  }

  @Bean
  @Order(2)
  /** Configures the security filter chain for the API. */
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        // All endpoints are open for now, change this later when login is implemented.
        .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
        .formLogin(
            custom ->
                custom
                    .loginPage("/login.html")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")
                    .successHandler(
                        (request, response, authentication) -> {
                          // The redirect url can be stored in different places depending on the
                          // type
                          // of request,
                          // so two checks are required.
                          var cachedRequest = requestCache.getRequest(request, response);
                          String alternativeRedirect =
                              cachedRequest == null
                                  ? "http://localhost:5173/"
                                  : cachedRequest.getRedirectUrl();
                          String authorizeRequestUrl =
                              (String) request.getSession().getAttribute("ORIGINAL_REQUEST_URL");
                          response.sendRedirect(
                              authorizeRequestUrl != null
                                  ? authorizeRequestUrl
                                  : alternativeRedirect);
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
