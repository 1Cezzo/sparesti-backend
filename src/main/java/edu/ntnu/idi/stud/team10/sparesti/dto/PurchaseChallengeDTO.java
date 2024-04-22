package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import lombok.*;

/** A DTO for PurchaseChallenge entities. */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseChallengeDTO extends ChallengeDTO {
  private String productName;

  /**
   * The target amount for the challenge.
   *
   * @param savedChallenge The PurchaseChallenge entity to convert.
   */
  public PurchaseChallengeDTO(PurchaseChallenge savedChallenge) {
    this.setDescription(savedChallenge.getDescription());
    this.setTargetAmount(savedChallenge.getTargetAmount());
    this.setTimeInterval(savedChallenge.getTimeInterval());
    this.setDifficultyLevel(savedChallenge.getDifficultyLevel());
    this.setProductName(savedChallenge.getProductName());
  }

  /**
   * Constructor for creating a new PurchaseChallengeDTO.
   *
   * @param challenge The PurchaseChallenge entity to convert.
   */
  public PurchaseChallengeDTO(Challenge challenge) {
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
    challenge.setDescription(this.getDescription());
    challenge.setTargetAmount(this.getTargetAmount());
    challenge.setTimeInterval(this.getTimeInterval());
    challenge.setDifficultyLevel(this.getDifficultyLevel());
    challenge.setProductName(this.getProductName());
    return challenge;
  }
}
