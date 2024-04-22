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
   * The target amount for the challenge.
   *
   * @param savedChallenge The SavingChallenge entity to convert.
   */
  public SavingChallengeDTO(SavingChallenge savedChallenge) {
    this.setDescription(savedChallenge.getDescription());
    this.setTargetAmount(savedChallenge.getTargetAmount());
    this.setTimeInterval(savedChallenge.getTimeInterval());
    this.setDifficultyLevel(savedChallenge.getDifficultyLevel());
  }

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
    challenge.setDescription(this.getDescription());
    challenge.setTargetAmount(this.getTargetAmount());
    challenge.setTimeInterval(this.getTimeInterval());
    challenge.setDifficultyLevel(this.getDifficultyLevel());
    return challenge;
  }
}
