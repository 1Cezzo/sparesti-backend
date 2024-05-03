package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExistingUserExceptionTest {

  @Test
  public void testConstructorWithMessage() {
    String message = "User already exists";
    ExistingUserException exception = new ExistingUserException(message);
    Assertions.assertEquals(message, exception.getMessage());
    Assertions.assertNull(exception.getCause());
  }

  @Test
  public void testConstructorWithMessageAndCause() {
    String message = "User already exists";
    Throwable cause = new Throwable("Root cause");
    ExistingUserException exception = new ExistingUserException(message, cause);
    Assertions.assertEquals(message, exception.getMessage());
    Assertions.assertEquals(cause, exception.getCause());
  }
}
