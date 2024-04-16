package edu.ntnu.idi.stud.team10.sparesti.util;

public class InvalidIdException extends RuntimeException {
  public InvalidIdException(String message) {
    super(message);
  }

  public InvalidIdException(String message, Throwable cause) {
    super(message, cause);
  }
}
