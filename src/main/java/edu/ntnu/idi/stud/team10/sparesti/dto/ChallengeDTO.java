package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {
  private String description;
  private double targetAmount;
  private TimeInterval timeInterval;
  private DifficultyLevel difficultyLevel;

  /**
   * Converts the DTO to a Challenge entity.
   *
   * @return (Challenge) The Challenge entity.
   */
  public Challenge toEntity() {
    Challenge challenge = new Challenge();
    challenge.setDescription(this.description);
    challenge.setTargetAmount(this.targetAmount);
    challenge.setTimeInterval(this.timeInterval);
    challenge.setDifficultyLevel(this.difficultyLevel);
    return challenge;
  }
}
