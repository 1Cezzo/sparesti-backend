package edu.ntnu.idi.stud.team10.sparesti.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** A class that handles exceptions thrown by the REST API. */
@ControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleInvalidIdException(NotFoundException e) {
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

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorResponse res = new ErrorResponse();
    res.setTitle(e.getMessage());
    res.setStatus(HttpStatus.BAD_REQUEST.value());
    res.setTimestamp(java.time.LocalDate.now().toString());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
    ErrorResponse res = new ErrorResponse();
    res.setTitle(e.getMessage());
    res.setStatus(HttpStatus.CONFLICT.value());
    res.setTimestamp(java.time.LocalDate.now().toString());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
    ErrorResponse res = new ErrorResponse();
    res.setTitle(e.getMessage());
    res.setStatus(HttpStatus.UNAUTHORIZED.value());
    res.setTimestamp(java.time.LocalDate.now().toString());

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
  }
}
