package edu.ntnu.idi.stud.team10.sparesti.util;

/** An exception that is thrown when something is not found */
public class NotFoundException extends RuntimeException {
  /**
   * Constructor for NotFoundException.
   *
   * @param message The exception message.
   */
  public NotFoundException(String message) {
    super(message);
  }

  /**
   * Constructor for NotFoundException.
   *
   * @param message The exception message.
   * @param cause The exception cause.
   */
  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
