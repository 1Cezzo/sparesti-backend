package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorResponseTest {

  @Test
  public void testNoArgsConstructor() {
    ErrorResponse errorResponse = new ErrorResponse();
    Assertions.assertNull(errorResponse.getTitle());
    Assertions.assertEquals(0, errorResponse.getStatus());
    Assertions.assertNull(errorResponse.getTimestamp());
  }

  @Test
  public void testAllArgsConstructor() {
    ErrorResponse errorResponse = new ErrorResponse("Error", 404, "2024-05-03T12:00:00");
    Assertions.assertEquals("Error", errorResponse.getTitle());
    Assertions.assertEquals(404, errorResponse.getStatus());
    Assertions.assertEquals("2024-05-03T12:00:00", errorResponse.getTimestamp());
  }

  @Test
  public void testSetTitle() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTitle("Error");
    Assertions.assertEquals("Error", errorResponse.getTitle());
  }

  @Test
  public void testSetStatus() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(404);
    Assertions.assertEquals(404, errorResponse.getStatus());
  }

  @Test
  public void testSetTimestamp() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTimestamp("2024-05-03T12:00:00");
    Assertions.assertEquals("2024-05-03T12:00:00", errorResponse.getTimestamp());
  }

  @Test
  public void testCanEqual() {
    ErrorResponse errorResponse = new ErrorResponse();
    Assertions.assertTrue(errorResponse.canEqual(new ErrorResponse()));
  }

  @Test
  public void testEquals() {
    ErrorResponse errorResponse = new ErrorResponse();
    Assertions.assertTrue(errorResponse.equals(new ErrorResponse()));

    ErrorResponse errorResponse1 = new ErrorResponse();
    errorResponse1.setTitle("Error");
    errorResponse1.setStatus(404);
    errorResponse1.setTimestamp("2024-05-03T12:00:00");

    ErrorResponse errorResponse2 = new ErrorResponse();
    errorResponse2.setTitle("Error");
    errorResponse2.setStatus(404);
    errorResponse2.setTimestamp("2024-05-03T12:00:00");

    ErrorResponse errorResponse3 = new ErrorResponse();
    errorResponse3.setTitle("Error");
    errorResponse3.setStatus(404);
    errorResponse3.setTimestamp("2024-05-03T12:00:01");

    Assertions.assertTrue(errorResponse1.equals(errorResponse2));
    Assertions.assertFalse(errorResponse1.equals(errorResponse3));
    Assertions.assertFalse(errorResponse1.equals(null));
  }

  @Test
  public void testGetters() {
    ErrorResponse errorResponse = new ErrorResponse();
    Assertions.assertNull(errorResponse.getTitle());
    Assertions.assertEquals(0, errorResponse.getStatus());
    Assertions.assertNull(errorResponse.getTimestamp());
  }

  @Test
  public void testHashCode() {
    ErrorResponse errorResponse1 = new ErrorResponse();
    errorResponse1.setTitle("Error");
    errorResponse1.setStatus(404);
    errorResponse1.setTimestamp("2024-05-03T12:00:00");

    ErrorResponse errorResponse2 = new ErrorResponse();
    errorResponse2.setTitle("Error");
    errorResponse2.setStatus(404);
    errorResponse2.setTimestamp("2024-05-03T12:00:00");

    Assertions.assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
  }

  @Test
  public void testToString() {
    ErrorResponse errorResponse = new ErrorResponse();
    Assertions.assertEquals(
        "ErrorResponse(title=null, status=0, timestamp=null)", errorResponse.toString());
  }
}
