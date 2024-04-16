package edu.ntnu.idi.stud.team10.sparesti.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(InvalidIdException.class)
  public ResponseEntity<ErrorResponse> handleInvalidIdException(InvalidIdException e) {
    ErrorResponse res = new ErrorResponse();
    res.setTitle(e.getMessage());
    res.setStatus(HttpStatus.NOT_FOUND.value());
    res.setTimestamp(java.time.LocalDate.now().toString());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
  }

  @ExceptionHandler(ExistingUserException.class)
  public ResponseEntity<ErrorResponse> handleExistingUserException(ExistingUserException e) {
    ErrorResponse res = new ErrorResponse();
    res.setTitle(e.getMessage());
    res.setStatus(HttpStatus.CONFLICT.value());
    res.setTimestamp(java.time.LocalDate.now().toString());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
  }
}
