package edu.ntnu.idi.stud.team10.sparesti.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** An error response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private String title;
  private int status;
  private String timestamp;
}
