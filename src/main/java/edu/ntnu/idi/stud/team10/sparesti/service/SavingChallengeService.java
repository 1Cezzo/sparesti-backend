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

  @Transactional
  public SavingChallenge create(SavingChallengeDTO dto) {
    SavingChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = challengeService.calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    challenge.setCompleted(false);
    return savingChallengeRepository.save(challenge);
  }

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

  @Transactional
  public void delete(Long id) {
    Optional<SavingChallenge> optionalChallenge = savingChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      savingChallengeRepository.delete(optionalChallenge.get());
    } else {
      throw new IllegalArgumentException("Saving Challenge not found");
    }
  }

  @Transactional(readOnly = true)
  public List<SavingChallenge> getAll() {
    return savingChallengeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<SavingChallenge> getById(Long id) {
    return savingChallengeRepository.findById(id);
  }

  @Transactional
  public void addToSavedAmount(Long id, Double amount) {
    Optional<SavingChallenge> optionalChallenge = savingChallengeRepository.findById(id);
    if (optionalChallenge.isPresent()) {
      SavingChallenge challenge = optionalChallenge.get();
      challenge.setSavedAmount(challenge.getSavedAmount() + amount);
      challenge.setCompleted(challenge.getSavedAmount() >= challenge.getTargetAmount());
      savingChallengeRepository.save(challenge);
    } else {
      throw new IllegalArgumentException("Saving Challenge not found");
    }
  }
}
