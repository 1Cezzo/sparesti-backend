package edu.ntnu.idi.stud.team10.sparesti.util;

public class ExistingUserException extends RuntimeException {
  public ExistingUserException(String message) {
    super(message);
  }

  public ExistingUserException(String message, Throwable cause) {
    super(message, cause);
  }
}
