package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotFoundExceptionTest {

  @Test
  public void testConstructorWithMessage() {
    String message = "Resource not found";
    NotFoundException exception = new NotFoundException(message);
    Assertions.assertEquals(message, exception.getMessage());
    Assertions.assertNull(exception.getCause());
  }

  @Test
  public void testConstructorWithMessageAndCause() {
    String message = "Resource not found";
    Throwable cause = new Throwable("Root cause");
    NotFoundException exception = new NotFoundException(message, cause);
    Assertions.assertEquals(message, exception.getMessage());
    Assertions.assertEquals(cause, exception.getCause());
  }
}
