package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.PurchaseChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Purchase Challenge entities. */
@Service
public class PurchaseChallengeService {

  private final PurchaseChallengeRepository purchaseChallengeRepository;
  private final ChallengeService challengeService;

  @Autowired
  public PurchaseChallengeService(
      PurchaseChallengeRepository purchaseChallengeRepository, ChallengeService challengeService) {
    this.purchaseChallengeRepository = purchaseChallengeRepository;
    this.challengeService = challengeService;
  }

  /**
   * Create a new purchase challenge.
   *
   * @param dto the DTO representing the purchase challenge to create.
   * @return the created purchase challenge.
   */
  @Transactional
  public PurchaseChallenge create(PurchaseChallengeDTO dto) {
    PurchaseChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = challengeService.calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    challenge.setCompleted(false);
    return purchaseChallengeRepository.save(challenge);
  }

  /**
   * Update a purchase challenge.
   *
   * @param id the id of the purchase challenge.
   * @param purchaseChallengeDTO the DTO representing the purchase challenge to update.
   * @return the updated purchase challenge.
   */
  @Transactional
  public PurchaseChallenge update(Long id, PurchaseChallengeDTO purchaseChallengeDTO) {
    Optional<PurchaseChallenge> optionalChallenge = purchaseChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      PurchaseChallenge existingChallenge = optionalChallenge.get();

      if (purchaseChallengeDTO.getDescription() != null) {
        existingChallenge.setDescription(purchaseChallengeDTO.getDescription());
      }

      existingChallenge.setTargetAmount(purchaseChallengeDTO.getTargetAmount());
      existingChallenge.setSavedAmount(purchaseChallengeDTO.getSavedAmount());

      if (purchaseChallengeDTO.getMediaUrl() != null) {
        existingChallenge.setMediaUrl(purchaseChallengeDTO.getMediaUrl());
      }

      if (purchaseChallengeDTO.getTimeInterval() != null) {
        existingChallenge.setTimeInterval(purchaseChallengeDTO.getTimeInterval());
        LocalDate expiryDate =
            challengeService.calculateExpiryDate(existingChallenge.getTimeInterval());
        existingChallenge.setExpiryDate(expiryDate);
      }

      if (purchaseChallengeDTO.getProductName() != null) {
        existingChallenge.setProductName(purchaseChallengeDTO.getProductName());
      }

      return purchaseChallengeRepository.save(existingChallenge);
    } else {
      throw new IllegalArgumentException("Purchase Challenge not found");
    }
  }

  /**
   * Delete a purchase challenge.
   *
   * @param id the id of the purchase challenge.
   */
  @Transactional
  public void delete(Long id) {
    Optional<PurchaseChallenge> optionalChallenge = purchaseChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      purchaseChallengeRepository.delete(optionalChallenge.get());
    } else {
      throw new IllegalArgumentException("Purchase Challenge not found");
    }
  }

  /**
   * Get all purchase challenges.
   *
   * @return a list of all purchase challenges.
   */
  @Transactional(readOnly = true)
  public List<PurchaseChallenge> getAll() {
    return purchaseChallengeRepository.findAll();
  }

  /**
   * Get a purchase challenge by id.
   *
   * @param id the id of the purchase challenge.
   * @return the purchase challenge if it exists, or an empty Optional otherwise.
   */
  @Transactional(readOnly = true)
  public PurchaseChallenge getById(Long id) {
    return purchaseChallengeRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Challenge with id " + id + " not found."));
  }

  /**
   * Add amount to the saved amount of a purchase challenge.
   *
   * @param id the id of the purchase challenge.
   * @param amount the amount to add.
   */
  @Transactional
  public void addToSavedAmount(Long id, Double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be positive.");
    }

    PurchaseChallenge challenge =
        purchaseChallengeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Challenge with id " + id + " not found."));
    challenge.setSavedAmount(challenge.getSavedAmount() + amount);
    challenge.setCompleted(challenge.getSavedAmount() >= challenge.getTargetAmount());
    purchaseChallengeRepository.save(challenge);
  }
}
