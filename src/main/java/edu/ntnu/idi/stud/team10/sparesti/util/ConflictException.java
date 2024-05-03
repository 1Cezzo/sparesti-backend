package edu.ntnu.idi.stud.team10.sparesti.util;

/** An exception thrown when a conflict occurs. */
public class ConflictException extends RuntimeException {
  /**
   * Constructor for ConflictException.
   *
   * @param message The exception message.
   */
  public ConflictException(String message) {
    super(message);
  }

  /**
   * Constructor for ConflictException.
   *
   * @param message The exception message.
   * @param cause The exception cause.
   */
  public ConflictException(String message, Throwable cause) {
    super(message, cause);
  }
}
