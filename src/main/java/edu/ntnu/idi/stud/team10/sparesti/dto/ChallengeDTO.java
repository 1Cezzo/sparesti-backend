package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import lombok.*;

/** Data transfer object for Challenge entities. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {
  private Long id;
  private String title;
  private String description;
  private double targetAmount;
  private double savedAmount;
  private String mediaUrl;
  private TimeInterval timeInterval;
  private DifficultyLevel difficultyLevel;
  private LocalDate expiryDate;
  private boolean completed;

  /**
   * Constructor for creating a new ChallengeDTO.
   *
   * @param challenge The Challenge entity to convert.
   */
  public ChallengeDTO(Challenge challenge) {
    this.id = challenge.getId();
    this.title = challenge.getTitle();
    this.description = challenge.getDescription();
    this.targetAmount = challenge.getTargetAmount();
    this.savedAmount = challenge.getSavedAmount();
    this.mediaUrl = challenge.getMediaUrl();
    this.timeInterval = challenge.getTimeInterval();
    this.difficultyLevel = challenge.getDifficultyLevel();
    this.expiryDate = challenge.getExpiryDate();
    this.completed = challenge.isCompleted();
  }

  /**
   * Converts the DTO to a Challenge entity.
   *
   * @return (Challenge) The Challenge entity.
   */
  public Challenge toEntity() {
    Challenge challenge = new Challenge();
    challenge.setId(this.id);
    challenge.setTitle(this.title);
    challenge.setDescription(this.description);
    challenge.setTargetAmount(this.targetAmount);
    challenge.setSavedAmount(this.savedAmount);
    challenge.setMediaUrl(this.mediaUrl);
    challenge.setTimeInterval(this.timeInterval);
    challenge.setDifficultyLevel(this.difficultyLevel);
    challenge.setExpiryDate(this.expiryDate);
    challenge.setCompleted(this.completed);
    return challenge;
  }
}
