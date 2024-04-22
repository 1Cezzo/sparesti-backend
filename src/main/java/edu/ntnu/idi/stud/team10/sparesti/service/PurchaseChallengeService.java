package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.PurchaseChallengeRepository;

/** Service for Purchase Challenge entities. */
@Service
public class PurchaseChallengeService extends ChallengeService<PurchaseChallenge> {

  public PurchaseChallengeService(PurchaseChallengeRepository purchaseChallengeRepository) {
    super(purchaseChallengeRepository);
  }

  /**
   * Create a new purchase challenge.
   *
   * @param entity the purchase challenge to create.
   * @return the created purchase challenge.
   */
  @Override
  public PurchaseChallenge createChallenge(PurchaseChallenge entity) {
    return super.createChallenge(entity);
  }

  /**
   * Update a purchase challenge.
   *
   * @param id the id of the purchase challenge.
   * @param purchaseChallengeDTO the DTO representing the purchase challenge to update.
   * @return the updated purchase challenge.
   */
  public PurchaseChallenge updatePurchaseChallenge(
      Long id, PurchaseChallengeDTO purchaseChallengeDTO) {
    PurchaseChallenge updatedEntity = purchaseChallengeDTO.toEntity();
    return super.updateChallenge(id, updatedEntity);
  }

  /**
   * Delete a purchase challenge.
   *
   * @param id the id of the purchase challenge.
   */
  public void deletePurchaseChallenge(Long id) {
    super.deleteChallenge(id);
  }

  /**
   * Get all purchase challenges.
   *
   * @return a list of all purchase challenges.
   */
  public List<PurchaseChallenge> getAllPurchaseChallenges() {
    return super.getAll();
  }

  /**
   * Get a purchase challenge by id.
   *
   * @param id the id of the purchase challenge.
   * @return the purchase challenge if it exists, or an empty Optional otherwise.
   */
  public Optional<PurchaseChallenge> getPurchaseChallengeById(Long id) {
    return super.getById(id);
  }

  /**
   * Add amount to the saved amount of a purchase challenge.
   *
   * @param id the id of the purchase challenge.
   * @param amount the amount to add.
   */
  public void addToSavedAmount(Long id, double amount) {
    super.addToSavedAmount(id, amount);
  }
}
