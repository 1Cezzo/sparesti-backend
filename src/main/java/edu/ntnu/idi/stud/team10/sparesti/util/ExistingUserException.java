package edu.ntnu.idi.stud.team10.sparesti.util;

/** An exception thrown when a user already exists. */
public class ExistingUserException extends RuntimeException {
  /**
   * Constructor for ExistingUserException.
   *
   * @param message The exception message.
   */
  public ExistingUserException(String message) {
    super(message);
  }

  /**
   * Constructor for ExistingUserException.
   *
   * @param message The exception message.
   * @param cause The exception cause.
   */
  public ExistingUserException(String message, Throwable cause) {
    super(message, cause);
  }
}
