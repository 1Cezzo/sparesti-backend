package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.mockito.Mockito.*;

public class TokenParserTest {

  @Test
  void testExtractUserId() throws UnauthorizedException {
    // Arrange
    Jwt token = mock(Jwt.class);
    Long expectedUserId = 123L;
    when(token.getClaim(anyString())).thenReturn(expectedUserId);

    // Act
    Long actualUserId = TokenParser.extractUserId(token);

    // Assert
    Assertions.assertEquals(expectedUserId, actualUserId);
    verify(token, times(1)).getClaim(anyString());
  }

  @Test
  void testExtractUserIdThrowsUnauthorizedException() {
    // Arrange
    Jwt token = mock(Jwt.class);
    when(token.getClaim(anyString())).thenThrow(new RuntimeException());

    // Act & Assert
    Assertions.assertThrows(UnauthorizedException.class, () -> TokenParser.extractUserId(token));
    verify(token, times(1)).getClaim(anyString());
  }
}
