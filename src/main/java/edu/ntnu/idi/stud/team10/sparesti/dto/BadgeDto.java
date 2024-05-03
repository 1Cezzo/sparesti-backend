package edu.ntnu.idi.stud.team10.sparesti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data transfer object for Badge entities */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
}
