package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import lombok.*;

/** Data transfer object for Challenge entities. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDto {
  private Long id;
  private String title;
  private String description;
  private Double targetAmount;
  private Double usedAmount;
  private String mediaUrl;
  private TimeInterval timeInterval;
  private DifficultyLevel difficultyLevel;
  private LocalDate expiryDate;
  private boolean completed;
}
