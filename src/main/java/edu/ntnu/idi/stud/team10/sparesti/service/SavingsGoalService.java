package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDTO;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;

/** Service for Savings Goal entities. */
@Service
public class SavingsGoalService {

  private final SavingsGoalRepository savingsGoalRepository;

  @Autowired
  public SavingsGoalService(SavingsGoalRepository savingsGoalRepository) {
    this.savingsGoalRepository = savingsGoalRepository;
  }

  /**
   * Creates a new SavingsGoal entity.
   *
   * @param savingsGoalDTO the DTO representing the savings goal to create
   * @throws IllegalArgumentException if the target amount is less than or equal to 0
   * @return the created savings goal
   */
  public SavingsGoal createSavingsGoal(SavingsGoalDTO savingsGoalDTO) {
    SavingsGoal savingsGoal = savingsGoalDTO.toEntity();
    if (savingsGoalDTO.getTargetAmount() <= 0) {
      throw new IllegalArgumentException("Target amount must be greater than 0");
    }
    savingsGoal.setSavedAmount(0);
    savingsGoal.setCompleted(false);
    // Perform validation if necessary
    return savingsGoalRepository.save(savingsGoal);
  }

  /** Retrieves all SavingsGoal entities. */
  public List<SavingsGoal> getAllSavingsGoals() {
    return savingsGoalRepository.findAll();
  }

  /**
   * Retrieves a SavingsGoal entity by its ID.
   *
   * @param id the ID of the savings goal
   * @return the savings goal if it exists, or an empty Optional otherwise
   */
  public Optional<SavingsGoal> getSavingsGoalById(Long id) {
    return savingsGoalRepository.findById(id);
  }

  /**
   * Updates a SavingsGoal entity.
   *
   * @param id the ID of the savings goal
   * @param savingsGoalDTO the DTO representing the savings goal to update
   * @throws IllegalArgumentException if the target amount is less than or equal to 0 or the savings
   *     goal is null.
   * @return the updated savings goal
   */
  public SavingsGoal updateSavingsGoal(Long id, SavingsGoalDTO savingsGoalDTO) {
    Optional<SavingsGoal> optionalSavingsGoal = savingsGoalRepository.findById(id);
    if (savingsGoalDTO == null) {
      throw new IllegalArgumentException("SavingsGoalDTO cannot be null");
    }
    if (savingsGoalDTO.getTargetAmount() <= 0) {
      throw new IllegalArgumentException("Target amount must be greater than 0");
    }
    if (optionalSavingsGoal.isPresent()) {
      SavingsGoal savingsGoal = optionalSavingsGoal.get();
      savingsGoal.setName(savingsGoalDTO.getName());
      savingsGoal.setTargetAmount(savingsGoalDTO.getTargetAmount());
      savingsGoal.setSavedAmount(savingsGoalDTO.getSavedAmount());
      savingsGoal.setMediaUrl(savingsGoalDTO.getMediaUrl());
      savingsGoal.setDeadline(savingsGoalDTO.getDeadline());
      LocalDateTime currentDate = LocalDateTime.now();
      savingsGoal.setCompleted(
          savingsGoalDTO.getSavedAmount() > savingsGoalDTO.getTargetAmount()
              || currentDate.isAfter(savingsGoalDTO.getDeadline().atStartOfDay()));
      return savingsGoalRepository.save(savingsGoal);
    } else {
      throw new IllegalArgumentException("Savings goal with ID " + id + " not found");
    }
  }

  /**
   * Deletes a SavingsGoal entity by its ID.
   *
   * @param id the ID of the savings goal
   */
  public void deleteSavingsGoalById(Long id) {
    try {
      savingsGoalRepository.deleteById(id);
    } catch (Exception e) {
      throw new IllegalArgumentException("Savings goal with ID " + id + " not found");
    }
  }

  /**
   * Updates the saved amount of a savings goal.
   *
   * @param id The ID of the savings goal.
   */
  public void updateSavedAmount(Long id, double savedAmount) {
    Optional<SavingsGoal> optionalSavingsGoal = savingsGoalRepository.findById(id);
    if (optionalSavingsGoal.isPresent()) {
      SavingsGoal savingsGoal = optionalSavingsGoal.get();
      savingsGoal.setSavedAmount(savingsGoal.getSavedAmount() + savedAmount);
      LocalDateTime currentDate = LocalDateTime.now();
      savingsGoal.setCompleted(
          savingsGoal.getSavedAmount() > savingsGoal.getTargetAmount()
              || currentDate.isAfter(savingsGoal.getDeadline().atStartOfDay()));
      savingsGoalRepository.save(savingsGoal);
    } else {
      throw new IllegalArgumentException("Savings goal with ID " + id + " not found");
    }
  }
}
