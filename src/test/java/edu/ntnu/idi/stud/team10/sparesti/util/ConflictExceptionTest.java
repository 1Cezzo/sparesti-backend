package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConflictExceptionTest {

  @Test
  public void testConstructorWithMessage() {
    String message = "Conflict occurred";
    ConflictException exception = new ConflictException(message);
    Assertions.assertEquals(message, exception.getMessage());
    Assertions.assertNull(exception.getCause());
  }

  @Test
  public void testConstructorWithMessageAndCause() {
    String message = "Conflict occurred";
    Throwable cause = new Throwable("Root cause");
    ConflictException exception = new ConflictException(message, cause);
    Assertions.assertEquals(message, exception.getMessage());
    Assertions.assertEquals(cause, exception.getCause());
  }
}
