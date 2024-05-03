package edu.ntnu.idi.stud.team10.sparesti.dto;

import lombok.*;

/** A DTO for PurchaseChallenge entities. */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseChallengeDto extends ChallengeDto {
  private String productName;
  private double productPrice;
}
