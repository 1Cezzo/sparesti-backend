package edu.ntnu.idi.stud.team10.sparesti.util;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestExceptionHandlerTest {

  private RestExceptionHandler restExceptionHandler;

  @BeforeEach
  public void setUp() {
    restExceptionHandler = new RestExceptionHandler();
  }

  @Test
  public void testHandleNotFoundException() {
    NotFoundException exception = new NotFoundException("Resource not found");
    ResponseEntity<ErrorResponse> responseEntity =
        restExceptionHandler.handleInvalidIdException(exception);
    ErrorResponse response = responseEntity.getBody();

    Assertions.assertNotNull(response);
    Assertions.assertEquals("Resource not found", response.getTitle());
    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    Assertions.assertEquals(LocalDate.now().toString(), response.getTimestamp());
  }

  @Test
  public void testHandleExistingUserException() {
    ExistingUserException exception = new ExistingUserException("User already exists");
    ResponseEntity<ErrorResponse> responseEntity =
        restExceptionHandler.handleExistingUserException(exception);
    ErrorResponse response = responseEntity.getBody();

    Assertions.assertNotNull(response);
    Assertions.assertEquals("User already exists", response.getTitle());
    Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    Assertions.assertEquals(LocalDate.now().toString(), response.getTimestamp());
  }

  @Test
  public void testHandleIllegalArgumentException() {
    IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");
    ResponseEntity<ErrorResponse> responseEntity =
        restExceptionHandler.handleIllegalArgumentException(exception);
    ErrorResponse response = responseEntity.getBody();

    Assertions.assertNotNull(response);
    Assertions.assertEquals("Invalid argument", response.getTitle());
    Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    Assertions.assertEquals(LocalDate.now().toString(), response.getTimestamp());
  }

  @Test
  public void testHandleConflictException() {
    ConflictException exception = new ConflictException("Conflict occurred");
    ResponseEntity<ErrorResponse> responseEntity =
        restExceptionHandler.handleConflictException(exception);
    ErrorResponse response = responseEntity.getBody();

    Assertions.assertNotNull(response);
    Assertions.assertEquals("Conflict occurred", response.getTitle());
    Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    Assertions.assertEquals(LocalDate.now().toString(), response.getTimestamp());
  }

  @Test
  public void testHandleUnauthorizedException() {
    UnauthorizedException exception = new UnauthorizedException("Unauthorized access");
    ResponseEntity<ErrorResponse> responseEntity =
        restExceptionHandler.handleUnauthorizedException(exception);
    ErrorResponse response = responseEntity.getBody();

    Assertions.assertNotNull(response);
    Assertions.assertEquals("Unauthorized access", response.getTitle());
    Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    Assertions.assertEquals(LocalDate.now().toString(), response.getTimestamp());
  }
}
