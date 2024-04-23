package edu.ntnu.idi.stud.team10.sparesti.config;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.service.UserInfoService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
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
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/api/users/create")
            .permitAll()
            .requestMatchers("/login.html")
            .permitAll()
            .requestMatchers("/stylesheet.css")
            .permitAll()
            .requestMatchers("/script.js")
            .permitAll()
            .requestMatchers("/images/**")
            .permitAll()
            .anyRequest().authenticated())
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

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(
      UserInfoService userInfoService) {
    return (context) -> {
      if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
        UserInfoDto info = userInfoService.getUserInfoByEmail(context.getPrincipal().getName());
        OidcUserInfo userInfo = OidcUserInfo
            .builder()
            .email(context.getPrincipal().getName())
            .birthdate(info.getDateOfBirth().toString()).preferredUsername(info.getDisplayName())
            .build();
        context.getClaims().claims(claims ->
                                       claims.putAll(userInfo.getClaims()));
      }
    };
  }
}
