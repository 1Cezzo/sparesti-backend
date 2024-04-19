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

  @Transactional
  public ConsumptionChallenge create(ConsumptionChallengeDTO dto) {
    ConsumptionChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = challengeService.calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    challenge.setCompleted(false);
    return consumptionChallengeRepository.save(challenge);
  }

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
      throw new IllegalArgumentException("Consumption Challenge not found");
    }
  }

  @Transactional
  public void delete(Long id) {
    Optional<ConsumptionChallenge> optionalChallenge = consumptionChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      consumptionChallengeRepository.delete(optionalChallenge.get());
    } else {
      throw new IllegalArgumentException("Consumption Challenge not found");
    }
  }

  @Transactional(readOnly = true)
  public List<ConsumptionChallenge> getAll() {
    return consumptionChallengeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<ConsumptionChallenge> getById(Long id) {
    return consumptionChallengeRepository.findById(id);
  }

  @Transactional
  public void addToSavedAmount(Long id, double amount) {
    Optional<ConsumptionChallenge> optionalChallenge = consumptionChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      ConsumptionChallenge challenge = optionalChallenge.get();
      challenge.setSavedAmount(challenge.getSavedAmount() + amount);
      challenge.setCompleted(challenge.getSavedAmount() > challenge.getTargetAmount());
      consumptionChallengeRepository.save(challenge);
    } else {
      throw new IllegalArgumentException("Consumption Challenge not found");
    }
  }
}
