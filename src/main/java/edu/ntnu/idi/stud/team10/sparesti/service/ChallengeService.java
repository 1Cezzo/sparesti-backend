package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/**
 * Service for Challenge entities.
 *
 * @param <T> the type of the challenge.
 */
@Service
public abstract class ChallengeService<T extends Challenge> {

  private final ChallengeRepository<T> challengeRepository;

  /**
   * Constructs a ChallengeService with the necessary repository.
   *
   * @param challengeRepository Repository for accessing challenge data.
   */
  @Autowired
  protected ChallengeService(ChallengeRepository<T> challengeRepository) {
    this.challengeRepository = challengeRepository;
  }

  /**
   * Create a new challenge entity.
   *
   * @param entity the challenge entity to create.
   * @return the created challenge entity.
   */
  @Transactional
  protected T createChallenge(T entity) {
    TimeInterval timeInterval = entity.getTimeInterval();
    entity.setUsedAmount(0.0);
    LocalDate expiryDate = calculateExpiryDate(timeInterval);
    entity.setExpiryDate(expiryDate);
    entity.setCompleted(false);

    return challengeRepository.save(entity);
  }

  /**
   * Update a challenge entity.
   *
   * @param id the id of the challenge entity.
   * @param updatedEntity the updated challenge entity.
   * @return the updated challenge entity.
   * @param <E> the type of the updated entity.
   */
  @Transactional
  protected <E extends T> E updateChallenge(Long id, E updatedEntity) {
    if (updatedEntity.getClass().equals(Challenge.class)) {
      throw new IllegalArgumentException("Invalid entity type: Challenge");
    }

    Optional<T> optionalChallenge = challengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      T existingChallenge = optionalChallenge.get();
      // Update the existing challenge with the data from the updated entity
      if (updatedEntity.getTitle() != null) {
        existingChallenge.setTitle(updatedEntity.getTitle());
      }

      if (updatedEntity.getDescription() != null) {
        existingChallenge.setDescription(updatedEntity.getDescription());
      }

      if (updatedEntity.getTargetAmount() > 0) {
        existingChallenge.setTargetAmount(updatedEntity.getTargetAmount());
      }

      if (updatedEntity.getUsedAmount() > 0) {
        existingChallenge.setUsedAmount(updatedEntity.getUsedAmount());
      }

      if (updatedEntity.getMediaUrl() != null) {
        existingChallenge.setMediaUrl(updatedEntity.getMediaUrl());
      }

      if (updatedEntity.getTimeInterval() != null) {
        existingChallenge.setTimeInterval(updatedEntity.getTimeInterval());
        existingChallenge.setExpiryDate(calculateExpiryDate(updatedEntity.getTimeInterval()));
      }

      if (updatedEntity.getDifficultyLevel() != null) {
        existingChallenge.setDifficultyLevel(updatedEntity.getDifficultyLevel());
      }

      // Check if the entity is a ConsumptionChallenge to set its specific properties
      if (existingChallenge instanceof ConsumptionChallenge) {
        ConsumptionChallenge consumptionChallenge = (ConsumptionChallenge) existingChallenge;
        ConsumptionChallenge updatedConsumptionChallenge = (ConsumptionChallenge) updatedEntity;

        Optional.ofNullable(updatedConsumptionChallenge.getProductCategory())
            .ifPresent(consumptionChallenge::setProductCategory);
        Optional.ofNullable(updatedConsumptionChallenge.getReductionPercentage())
            .ifPresent(consumptionChallenge::setReductionPercentage);
      }

      if (existingChallenge instanceof PurchaseChallenge) {
        PurchaseChallenge purchaseChallenge = (PurchaseChallenge) existingChallenge;
        PurchaseChallenge updatedPurchaseChallenge = (PurchaseChallenge) updatedEntity;

        Optional.ofNullable(updatedPurchaseChallenge.getProductName())
            .ifPresent(purchaseChallenge::setProductName);
        purchaseChallenge.setProductName(updatedPurchaseChallenge.getProductName());
        Optional.ofNullable(updatedPurchaseChallenge.getProductPrice())
            .ifPresent(purchaseChallenge::setProductPrice);
      }

      // Save the updated challenge
      return (E) challengeRepository.save(existingChallenge);
    } else {
      throw new NotFoundException("Challenge not found");
    }
  }

  /**
   * Delete a challenge entity.
   *
   * @param id the id of the challenge entity.
   */
  @Transactional
  protected void deleteChallenge(Long id) {
    Optional<T> optionalChallenge = challengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      challengeRepository.delete(optionalChallenge.get());
    } else {
      throw new NotFoundException("Challenge not found");
    }
  }

  /**
   * Get all challenge entities.
   *
   * @return a list of all challenge entities.
   */
  @Transactional(readOnly = true)
  protected List<T> getAll() {
    return challengeRepository.findAll();
  }

  /**
   * Get a challenge entity by id.
   *
   * @param id the id of the challenge entity.
   * @return the challenge entity if it exists, or an empty Optional otherwise.
   */
  @Transactional(readOnly = true)
  protected Optional<T> getById(Long id) {
    return challengeRepository.findById(id);
  }

  /**
   * Add an amount to the saved amount of a challenge.
   *
   * @param id the id of the challenge.
   * @param amount the amount to add to the saved amount.
   */
  @Transactional
  protected void addToSavedAmount(Long id, double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be greater than or equal to 0");
    }
    Optional<T> optionalChallenge = challengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      T challenge = optionalChallenge.get();
      challenge.setUsedAmount(challenge.getUsedAmount() + amount);
      challengeRepository.save(challenge);
    } else {
      throw new NotFoundException("Challenge not found");
    }
  }

  /**
   * Calculate the expiry date of a challenge based on the time interval.
   *
   * @param timeInterval the time interval of the challenge.
   * @return the expiry date of the challenge.
   */
  protected LocalDate calculateExpiryDate(TimeInterval timeInterval) {
    LocalDate currentDate = LocalDate.now();
    return switch (timeInterval) {
      case DAILY -> currentDate.plusDays(1);
      case WEEKLY -> currentDate.plusWeeks(1);
      case MONTHLY -> currentDate.plusMonths(1);
      default -> currentDate;
    };
  }
}
