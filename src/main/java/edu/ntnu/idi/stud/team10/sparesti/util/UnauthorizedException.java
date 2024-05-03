package edu.ntnu.idi.stud.team10.sparesti.util;

/** An exception thrown when a user is not authorized. */
public class UnauthorizedException extends RuntimeException {
  /**
   * Constructor for UnauthorizedException.
   *
   * @param message The exception message.
   */
  public UnauthorizedException(String message) {
    super(message);
  }

  /**
   * Constructor for UnauthorizedException.
   *
   * @param message The exception message.
   * @param cause The exception cause.
   */
  public UnauthorizedException(String message, Throwable cause) {
    super(message, cause);
  }
}
