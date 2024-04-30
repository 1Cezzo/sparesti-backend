package edu.ntnu.idi.stud.team10.sparesti.util;

import static edu.ntnu.idi.stud.team10.sparesti.config.AuthorizationServerConfig.USER_ID_CLAIM;

import org.springframework.security.oauth2.jwt.Jwt;

public class TokenParser {
  private TokenParser() {}

  public static Long extractUserId(Jwt token) throws UnauthorizedException {
    try {
      return token.getClaim(USER_ID_CLAIM);
    } catch (Exception e) {
      throw new UnauthorizedException("No access token found.");
    }
  }
}
