package edu.ntnu.idi.stud.team10.sparesti.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserInfoService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;

/** Configuration for the Authorization Server. */
@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig {
  private final UserInfoService userInfoService;
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final SessionRegistry sessionRegistry;
  private final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);
  private final String frontendUrl;

  public static final String USER_ID_CLAIM = "userId";

  @Autowired
  public AuthorizationServerConfig(
      UserInfoService userInfoService,
      UserService userService,
      BCryptPasswordEncoder passwordEncoder,
      SessionRegistry sessionRegistry,
      Environment env) {
    this.userInfoService = userInfoService;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.sessionRegistry = sessionRegistry;
    this.frontendUrl = env.getProperty("frontend.url");
  }

  /**
   * Configures the security filter chain for the Authorization Server.
   *
   * @param http The HttpSecurity object to configure
   * @return The SecurityFilterChain object
   * @throws Exception If an error occurs
   */
  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
      throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(
            oidc ->
                oidc.userInfoEndpoint(
                    infoEndpoint ->
                        infoEndpoint.userInfoMapper(
                            context -> {
                              boolean userInfoRegistered =
                                  userInfoService.userInfoExistsByEmail(
                                      context.getAuthorization().getPrincipalName());
                              if (userInfoRegistered) {
                                UserInfoDto info =
                                    userInfoService.getUserInfoByEmail(
                                        context.getAuthorization().getPrincipalName());
                                return OidcUserInfo.builder()
                                    .email(context.getAuthorization().getPrincipalName())
                                    .givenName(info.getFirstName())
                                    .familyName(info.getLastName())
                                    .preferredUsername(info.getDisplayName())
                                    .build();
                              } else {
                                return OidcUserInfo.builder()
                                    .email(context.getAuthorization().getPrincipalName())
                                    .build();
                              }
                            }))); // Enable OpenID Connect 1.0
    http
        // Redirect to the login page.
        // Stores the original request URL in the session.
        .exceptionHandling(
            exceptions ->
                exceptions.authenticationEntryPoint(
                    (request, response, e) -> {
                      String originalUrl =
                          request.getRequestURL().toString() + "?" + request.getQueryString();
                      request.getSession().setAttribute("ORIGINAL_REQUEST_URL", originalUrl);
                      response.sendRedirect("/login");
                    }))
        // Accept access tokens for User Info and/or Client Registration
        .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

    return http.build();
  }

  /**
   * Configures the RegisteredClientRepository bean.
   *
   * @return The RegisteredClientRepository bean
   */
  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient oidcClient =
        RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("sparestiClient")
            .clientSecret("{noop}somesecret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(frontendUrl + "/token")
            .postLogoutRedirectUri(frontendUrl)
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .clientSettings(
                ClientSettings.builder()
                    .requireAuthorizationConsent(true)
                    .requireProofKey(true)
                    .build())
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.of(180, ChronoUnit.MINUTES))
                    .build())
            .build();
    RegisteredClient swaggerClient =
        RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("swagger")
            .clientSecret(passwordEncoder.encode("secret"))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.of(180, ChronoUnit.MINUTES))
                    .build())
            .build();

    return new InMemoryRegisteredClientRepository(oidcClient, swaggerClient);
  }

  /**
   * Configures the JWKSource bean.
   *
   * @return The JWKSource bean
   */
  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    RSAKey rsaKey =
        new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  /**
   * Generates an RSA key pair.
   *
   * @return The generated RSA key pair
   */
  private static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }

  /**
   * Configures the JwtDecoder bean.
   *
   * @param jwkSource The JWKSource bean
   * @return The JwtDecoder bean
   */
  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  /**
   * Configures the AuthorizationServerSettings bean.
   *
   * @return The AuthorizationServerSettings bean
   */
  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .tokenRevocationEndpoint("/oauth2/revoke")
        .oidcLogoutEndpoint("/connect/logout")
        .authorizationEndpoint("/oauth2/authorize")
        .tokenEndpoint("/oauth2/token")
        .oidcUserInfoEndpoint("/userinfo")
        .build();
  }

  /**
   * Token customizer to add user specific claims to the JWT.
   *
   * @return The OAuth2TokenCustomizer bean
   */
  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
    return context -> {
      Object details = context.getPrincipal().getDetails();
      String sessionId;
      String principalName;
      try {
        WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
        sessionId = webDetails.getSessionId();
        SessionInformation info = sessionRegistry.getSessionInformation(sessionId);
        principalName = ((User) info.getPrincipal()).getUsername();
      } catch (Exception ignored) {
        principalName = null;
      }

      // The userId is extracted from the UserService using the name from the authentication
      // process,
      // by using the Http session stored in SessionRegistry or the principal name from the context.
      principalName = principalName == null ? context.getPrincipal().getName() : principalName;
      JwtClaimsSet.Builder claimsBuilder = context.getClaims();
      if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
        String finalPrincipalName = principalName;
        claimsBuilder.claims(
            claims -> {
              Long userId;
              try {
                UserDto user = userService.getUserByEmail(finalPrincipalName);
                userId = user.getId();
              } catch (Exception e) {
                logger.error(e.getMessage());
                userId = null;
              }
              claims.put(USER_ID_CLAIM, userId);
            });
      }
    };
  }
}
