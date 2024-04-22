package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingChallengeRepository;

/** Service for Saving Challenge entities. */
@Service
public class SavingChallengeService extends ChallengeService<SavingChallenge> {

  public SavingChallengeService(SavingChallengeRepository savingChallengeRepository) {
    super(savingChallengeRepository);
  }

  /**
   * Create a new saving challenge.
   *
   * @param entity the DTO representing the saving challenge to create.
   * @return the created saving challenge.
   */
  @Override
  public SavingChallenge createChallenge(SavingChallenge entity) {
    return super.createChallenge(entity);
  }

  /**
   * Update a saving challenge.
   *
   * @param id the id of the saving challenge.
   * @param savingChallengeDTO the DTO representing the saving challenge to update.
   * @return the updated saving challenge.
   */
  public SavingChallenge updateSavingChallenge(Long id, SavingChallengeDTO savingChallengeDTO) {
    SavingChallenge updatedEntity = savingChallengeDTO.toEntity();
    return super.updateChallenge(id, updatedEntity);
  }

  /**
   * Delete a saving challenge by id.
   *
   * @param id the id of the saving challenge.
   */
  public void deleteSavingChallenge(Long id) {
    super.deleteChallenge(id);
  }

  /**
   * Get all saving challenges.
   *
   * @return a list of all saving challenges.
   */
  public List<SavingChallenge> getAllSavingChallenges() {
    return super.getAll();
  }

  /**
   * Get a saving challenge by id.
   *
   * @param id the id of the saving challenge.
   * @return the saving challenge if it exists, or an empty Optional otherwise.
   */
  public Optional<SavingChallenge> getSavingChallengeById(Long id) {
    return super.getById(id);
  }

  /**
   * Add an amount to the saved amount of a saving challenge.
   *
   * @param id the id of the saving challenge.
   * @param amount the amount to add to the saved amount.
   */
  public void addToSavedAmount(Long id, double amount) {
    super.addToSavedAmount(id, amount);
  }
}
