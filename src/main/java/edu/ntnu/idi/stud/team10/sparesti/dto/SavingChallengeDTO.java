package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SavingChallengeDTO extends ChallengeDTO {

  public SavingChallengeDTO(SavingChallenge savedChallenge) {
    this.setDescription(savedChallenge.getDescription());
    this.setTargetAmount(savedChallenge.getTargetAmount());
    this.setTimeInterval(savedChallenge.getTimeInterval());
    this.setDifficultyLevel(savedChallenge.getDifficultyLevel());
  }

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
