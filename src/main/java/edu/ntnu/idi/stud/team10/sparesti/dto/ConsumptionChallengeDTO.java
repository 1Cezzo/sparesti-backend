package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionChallengeDTO extends ChallengeDTO {
  private String productCategory;
  private double reductionPercentage;

  public ConsumptionChallengeDTO(ConsumptionChallenge savedChallenge) {
    this.setDescription(savedChallenge.getDescription());
    this.setTargetAmount(savedChallenge.getTargetAmount());
    this.setTimeInterval(savedChallenge.getTimeInterval());
    this.setDifficultyLevel(savedChallenge.getDifficultyLevel());
    this.setProductCategory(savedChallenge.getProductCategory());
    this.setReductionPercentage(savedChallenge.getReductionPercentage());
  }

  @Override
  public ConsumptionChallenge toEntity() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    challenge.setDescription(this.getDescription());
    challenge.setTargetAmount(this.getTargetAmount());
    challenge.setTimeInterval(this.getTimeInterval());
    challenge.setDifficultyLevel(this.getDifficultyLevel());
    challenge.setProductCategory(this.getProductCategory());
    challenge.setReductionPercentage(this.getReductionPercentage());
    return challenge;
  }
}
