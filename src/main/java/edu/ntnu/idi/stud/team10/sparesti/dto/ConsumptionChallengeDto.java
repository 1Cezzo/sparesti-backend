package edu.ntnu.idi.stud.team10.sparesti.dto;

import lombok.*;

/** Data transfer object for ConsumptionChallenge entities. */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionChallengeDto extends ChallengeDto {
  private String productCategory;
  private Double reductionPercentage;
}
