package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Saving Challenge entities. */
@Service
public class SavingChallengeService {

  private final SavingChallengeRepository savingChallengeRepository;
  private final ChallengeService challengeService;

  @Autowired
  public SavingChallengeService(
      SavingChallengeRepository savingChallengeRepository, ChallengeService challengeService) {
    this.savingChallengeRepository = savingChallengeRepository;
    this.challengeService = challengeService;
  }

  /**
   * Create a new saving challenge.
   *
   * @param dto the DTO representing the saving challenge to create.
   * @return the created saving challenge.
   */
  @Transactional
  public SavingChallenge create(SavingChallengeDTO dto) {
    SavingChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = challengeService.calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    challenge.setCompleted(false);
    return savingChallengeRepository.save(challenge);
  }

  /**
   * Update a saving challenge.
   *
   * @param id the id of the saving challenge.
   * @param savingChallengeDTO the DTO representing the saving challenge to update.
   * @return the updated saving challenge.
   */
  @Transactional
  public SavingChallenge update(Long id, SavingChallengeDTO savingChallengeDTO) {
    Optional<SavingChallenge> optionalChallenge = savingChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      SavingChallenge existingChallenge = optionalChallenge.get();

      if (savingChallengeDTO.getDescription() != null) {
        existingChallenge.setDescription(savingChallengeDTO.getDescription());
      }

      existingChallenge.setTargetAmount(savingChallengeDTO.getTargetAmount());
      existingChallenge.setSavedAmount(savingChallengeDTO.getSavedAmount());

      if (savingChallengeDTO.getMediaUrl() != null) {
        existingChallenge.setMediaUrl(savingChallengeDTO.getMediaUrl());
      }

      if (savingChallengeDTO.getTimeInterval() != null) {
        existingChallenge.setTimeInterval(savingChallengeDTO.getTimeInterval());
        LocalDate expiryDate =
            challengeService.calculateExpiryDate(existingChallenge.getTimeInterval());
        existingChallenge.setExpiryDate(expiryDate);
      }

      if (savingChallengeDTO.getDifficultyLevel() != null) {
        existingChallenge.setDifficultyLevel(savingChallengeDTO.getDifficultyLevel());
      }

      return savingChallengeRepository.save(existingChallenge);
    } else {
      throw new IllegalArgumentException("Saving Challenge not found");
    }
  }

  /**
   * Delete a saving challenge by id.
   *
   * @param id the id of the saving challenge.
   */
  @Transactional
  public void delete(Long id) {
    Optional<SavingChallenge> optionalChallenge = savingChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      savingChallengeRepository.delete(optionalChallenge.get());
    } else {
      throw new IllegalArgumentException("Saving Challenge not found");
    }
  }

  /**
   * Get all saving challenges.
   *
   * @return a list of all saving challenges.
   */
  @Transactional(readOnly = true)
  public List<SavingChallenge> getAll() {
    return savingChallengeRepository.findAll();
  }

  /**
   * Get a saving challenge by id.
   *
   * @param id the id of the saving challenge.
   * @return the saving challenge if it exists, or an empty Optional otherwise.
   */
  @Transactional(readOnly = true)
  public SavingChallenge getById(Long id) {
    return savingChallengeRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Saving Challenge not found"));
  }

  /**
   * Add an amount to the saved amount of a saving challenge.
   *
   * @param id the id of the saving challenge.
   * @param amount the amount to add to the saved amount.
   * @throws IllegalArgumentException if the amount is negative.
   * @throws NotFoundException if the saving challenge is not found.
   */
  @Transactional
  public void addToSavedAmount(Long id, Double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }

    SavingChallenge challenge =
        savingChallengeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Saving Challenge not found"));
    challenge.setSavedAmount(challenge.getSavedAmount() + amount);
    challenge.setCompleted(challenge.getSavedAmount() >= challenge.getTargetAmount());
    savingChallengeRepository.save(challenge);
  }
}
