package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import lombok.*;

/** Data transfer object for SavingChallenge entities. */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SavingChallengeDTO extends ChallengeDTO {
  /**
   * Constructor for creating a new SavingChallengeDTO.
   *
   * @param challenge The SavingChallenge entity to convert.
   */
  public SavingChallengeDTO(Challenge challenge) {
    super(challenge);
  }

  /**
   * Converts the DTO to a SavingChallenge entity.
   *
   * @return (SavingChallenge) The SavingChallenge entity.
   */
  @Override
  public SavingChallenge toEntity() {
    SavingChallenge challenge = new SavingChallenge();
    challenge.setId(this.getId());
    challenge.setTitle(this.getTitle());
    challenge.setDescription(this.getDescription());
    challenge.setTargetAmount(this.getTargetAmount());
    challenge.setSavedAmount(this.getSavedAmount());
    challenge.setMediaUrl(this.getMediaUrl());
    challenge.setTimeInterval(this.getTimeInterval());
    challenge.setExpiryDate(this.getExpiryDate());
    challenge.setDifficultyLevel(this.getDifficultyLevel());
    challenge.setCompleted(this.isCompleted());
    return challenge;
  }
}
