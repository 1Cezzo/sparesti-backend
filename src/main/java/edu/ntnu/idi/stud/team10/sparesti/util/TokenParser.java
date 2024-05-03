package edu.ntnu.idi.stud.team10.sparesti.util;

import org.springframework.security.oauth2.jwt.Jwt;

import static edu.ntnu.idi.stud.team10.sparesti.config.AuthorizationServerConfig.USER_ID_CLAIM;

/** A utility class for parsing tokens. */
public class TokenParser {
  /** Constructor for TokenParser. */
  private TokenParser() {}

  /**
   * Extracts the user ID from a token.
   *
   * @param token The token to extract the user ID from.
   * @return The user ID.
   * @throws UnauthorizedException If the token does not contain a user ID.
   */
  public static Long extractUserId(Jwt token) throws UnauthorizedException {
    try {
      return token.getClaim(USER_ID_CLAIM);
    } catch (Exception e) {
      throw new UnauthorizedException("No access token found.");
    }
  }
}
