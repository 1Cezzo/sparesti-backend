package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import lombok.*;

/** A DTO for PurchaseChallenge entities. */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseChallengeDto extends ChallengeDto {
  private String productName;

  /**
   * Constructor for creating a new PurchaseChallengeDTO.
   *
   * @param challenge The PurchaseChallenge entity to convert.
   */
  public PurchaseChallengeDto(Challenge challenge) {
    super(challenge);
    if (challenge instanceof PurchaseChallenge purchaseChallenge) {
      this.productName = purchaseChallenge.getProductName();
    }
  }

  /**
   * Converts the DTO to a PurchaseChallenge entity.
   *
   * @return (PurchaseChallenge) The PurchaseChallenge entity.
   */
  @Override
  public PurchaseChallenge toEntity() {
    PurchaseChallenge challenge = new PurchaseChallenge();
    challenge.setId(this.getId());
    challenge.setTitle(this.getTitle());
    challenge.setDescription(this.getDescription());
    challenge.setTargetAmount(this.getTargetAmount());
    challenge.setUsedAmount(this.getUsedAmount());
    challenge.setMediaUrl(this.getMediaUrl());
    challenge.setTimeInterval(this.getTimeInterval());
    challenge.setExpiryDate(this.getExpiryDate());
    challenge.setDifficultyLevel(this.getDifficultyLevel());
    challenge.setProductName(this.getProductName());
    challenge.setCompleted(this.isCompleted());
    return challenge;
  }
}
