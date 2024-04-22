package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ConsumptionChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Consumption Challenge entities. */
@Service
public class ConsumptionChallengeService {

  private final ConsumptionChallengeRepository consumptionChallengeRepository;
  private final ChallengeService challengeService;

  @Autowired
  public ConsumptionChallengeService(
      ConsumptionChallengeRepository consumptionChallengeRepository,
      ChallengeService challengeService) {
    this.consumptionChallengeRepository = consumptionChallengeRepository;
    this.challengeService = challengeService;
  }

  /**
   * Create a new consumption challenge.
   *
   * @param dto the DTO representing the consumption challenge to create.
   * @return the created consumption challenge.
   */
  @Transactional
  public ConsumptionChallenge create(ConsumptionChallengeDTO dto) {
    ConsumptionChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = challengeService.calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    challenge.setCompleted(false);
    return consumptionChallengeRepository.save(challenge);
  }

  /**
   * Update a consumption challenge.
   *
   * @param id the id of the consumption challenge.
   * @param consumptionChallengeDTO the DTO representing the consumption challenge to update.
   * @return the updated consumption challenge.
   */
  @Transactional
  public ConsumptionChallenge update(Long id, ConsumptionChallengeDTO consumptionChallengeDTO) {
    Optional<ConsumptionChallenge> optionalChallenge = consumptionChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      ConsumptionChallenge existingChallenge = optionalChallenge.get();

      if (consumptionChallengeDTO.getDescription() != null) {
        existingChallenge.setDescription(consumptionChallengeDTO.getDescription());
      }

      existingChallenge.setTargetAmount(consumptionChallengeDTO.getTargetAmount());
      existingChallenge.setSavedAmount(consumptionChallengeDTO.getSavedAmount());

      if (consumptionChallengeDTO.getMediaUrl() != null) {
        existingChallenge.setMediaUrl(consumptionChallengeDTO.getMediaUrl());
      }

      if (consumptionChallengeDTO.getTimeInterval() != null) {
        existingChallenge.setTimeInterval(consumptionChallengeDTO.getTimeInterval());
        LocalDate expiryDate =
            challengeService.calculateExpiryDate(existingChallenge.getTimeInterval());
        existingChallenge.setExpiryDate(expiryDate);
      }

      if (consumptionChallengeDTO.getDifficultyLevel() != null) {
        existingChallenge.setDifficultyLevel(consumptionChallengeDTO.getDifficultyLevel());
      }

      if (consumptionChallengeDTO.getProductCategory() != null) {
        existingChallenge.setProductCategory(consumptionChallengeDTO.getProductCategory());
      }

      existingChallenge.setReductionPercentage(consumptionChallengeDTO.getReductionPercentage());

      return consumptionChallengeRepository.save(existingChallenge);
    } else {
      throw new NotFoundException("Consumption Challenge not found");
    }
  }

  /**
   * Delete a consumption challenge.
   *
   * @param id the id of the consumption challenge.
   */
  @Transactional
  public void delete(Long id) {
    ConsumptionChallenge challenge =
        consumptionChallengeRepository
            .findById(id)
            .orElseThrow(
                () -> new NotFoundException("Consumption challenge with id " + id + " not found."));
    consumptionChallengeRepository.delete(challenge);
  }

  /**
   * Get all consumption challenges.
   *
   * @return a list of all consumption challenges.
   */
  @Transactional(readOnly = true)
  public List<ConsumptionChallenge> getAll() {
    return consumptionChallengeRepository.findAll();
  }

  /**
   * Get a consumption challenge by id.
   *
   * @param id the id of the consumption challenge.
   * @return the consumption challenge if it exists, or an empty Optional otherwise.
   * @throws NotFoundException if the consumption challenge does not exist.
   */
  @Transactional(readOnly = true)
  public ConsumptionChallenge getById(Long id) {
    return consumptionChallengeRepository
        .findById(id)
        .orElseThrow(
            () -> new NotFoundException("Consumption challenge with id " + id + " not found."));
  }

  /**
   * Add to the saved amount of a consumption challenge.
   *
   * @param id the id of the consumption challenge.
   * @param amount the amount to add to the saved amount.
   * @throws IllegalArgumentException if the amount is negative.
   * @throws NotFoundException if the consumption challenge does not exist.
   */
  @Transactional
  public void addToSavedAmount(Long id, double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }

    ConsumptionChallenge challenge =
        consumptionChallengeRepository
            .findById(id)
            .orElseThrow(
                () -> new NotFoundException("Consumption challenge with id " + id + " not found."));
    challenge.setSavedAmount(challenge.getSavedAmount() + amount);
    challenge.setCompleted(challenge.getSavedAmount() > challenge.getTargetAmount());
    consumptionChallengeRepository.save(challenge);
  }
}
