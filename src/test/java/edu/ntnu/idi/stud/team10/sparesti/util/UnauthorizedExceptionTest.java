package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnauthorizedExceptionTest {

  @Test
  public void testConstructorWithMessage() {
    String message = "Unauthorized access";
    UnauthorizedException exception = new UnauthorizedException(message);
    Assertions.assertEquals(message, exception.getMessage());
  }

  @Test
  public void testConstructorWithMessageAndCause() {
    String message = "Unauthorized access";
    Throwable cause = new RuntimeException("Root cause");
    UnauthorizedException exception = new UnauthorizedException(message, cause);
    Assertions.assertEquals(message, exception.getMessage());
    Assertions.assertEquals(cause, exception.getCause());
  }
}
