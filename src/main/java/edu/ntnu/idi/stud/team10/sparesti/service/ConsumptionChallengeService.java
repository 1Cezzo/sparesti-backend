package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ConsumptionChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Consumption Challenge entities. */
@Service
public class ConsumptionChallengeService extends ChallengeService<ConsumptionChallenge> {

  public ConsumptionChallengeService(
      ConsumptionChallengeRepository consumptionChallengeRepository) {
    super(consumptionChallengeRepository);
  }

  /**
   * Create a new consumption challenge.
   *
   * @param entity the consumption challenge to create.
   * @return the created consumption challenge.
   */
  @Override
  public ConsumptionChallenge createChallenge(ConsumptionChallenge entity) {
    return super.createChallenge(entity);
  }

  /**
   * Update a consumption challenge.
   *
   * @param id the id of the consumption challenge.
   * @param consumptionChallengeDTO the DTO representing the consumption challenge to update.
   * @return the updated consumption challenge.
   */
  public ConsumptionChallenge updateConsumptionChallenge(
      Long id, ConsumptionChallengeDto consumptionChallengeDTO) {
    ConsumptionChallenge updatedEntity = consumptionChallengeDTO.toEntity();
    return super.updateChallenge(id, updatedEntity);
  }

  /**
   * Delete a consumption challenge.
   *
   * @param id the id of the consumption challenge.
   */
  public void deleteConsumptionChallenge(Long id) {
    super.deleteChallenge(id);
  }

  /**
   * Get all consumption challenges.
   *
   * @return a list of all consumption challenges.
   */
  public List<ConsumptionChallenge> getAllConsumptionChallenges() {
    return super.getAll();
  }

  /**
   * Get a consumption challenge by id.
   *
   * @param id the id of the consumption challenge.
   * @return the consumption challenge if it exists, or an empty Optional otherwise.
   * @throws NotFoundException if the consumption challenge does not exist.
   */
  public Optional<ConsumptionChallenge> getConsumptionChallengeById(Long id) {
    return super.getById(id);
  }

  /**
   * Add to the saved amount of a consumption challenge.
   *
   * @param id the id of the consumption challenge.
   * @param amount the amount to add to the saved amount.
   * @throws IllegalArgumentException if the amount is negative.
   * @throws NotFoundException if the consumption challenge does not exist.
   */
  public void addToSavedAmount(Long id, double amount) {
    super.addToSavedAmount(id, amount);
  }
}
