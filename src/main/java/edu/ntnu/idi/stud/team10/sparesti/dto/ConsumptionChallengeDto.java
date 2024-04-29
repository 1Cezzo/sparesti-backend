package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import lombok.*;

/** Data transfer object for ConsumptionChallenge entities. */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionChallengeDto extends ChallengeDto {
  private String productCategory;
  private double reductionPercentage;

  /**
   * Constructor for creating a new ConsumptionChallengeDTO.
   *
   * @param challenge The ConsumptionChallenge entity to convert.
   */
  public ConsumptionChallengeDto(Challenge challenge) {
    super(challenge);
    if (challenge instanceof ConsumptionChallenge consumptionChallenge) {
      this.productCategory = consumptionChallenge.getProductCategory();
      this.reductionPercentage = consumptionChallenge.getReductionPercentage();
    }
  }

  /**
   * Converts the DTO to a ConsumptionChallenge entity.
   *
   * @return (ConsumptionChallenge) The ConsumptionChallenge entity.
   */
  @Override
  public ConsumptionChallenge toEntity() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    challenge.setId(this.getId());
    challenge.setTitle(this.getTitle());
    challenge.setDescription(this.getDescription());
    challenge.setTargetAmount(this.getTargetAmount());
    challenge.setUsedAmount(this.getUsedAmount());
    challenge.setTimeInterval(this.getTimeInterval());
    challenge.setMediaUrl(this.getMediaUrl());
    challenge.setDifficultyLevel(this.getDifficultyLevel());
    challenge.setProductCategory(this.getProductCategory());
    challenge.setReductionPercentage(this.getReductionPercentage());
    challenge.setCompleted(this.isCompleted());
    return challenge;
  }
}
