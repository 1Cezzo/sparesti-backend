package edu.ntnu.idi.stud.team10.sparesti.service;

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
   * @return the created savings goal
   */
  public SavingsGoal createSavingsGoal(SavingsGoalDTO savingsGoalDTO) {
    SavingsGoal savingsGoal = savingsGoalDTO.toEntity();
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
   * @return the updated savings goal
   */
  public SavingsGoal updateSavingsGoal(Long id, SavingsGoalDTO savingsGoalDTO) {
    Optional<SavingsGoal> optionalSavingsGoal = savingsGoalRepository.findById(id);
    if (optionalSavingsGoal.isPresent()) {
      SavingsGoal savingsGoal = optionalSavingsGoal.get();
      savingsGoal.setName(savingsGoalDTO.getName());
      savingsGoal.setTargetAmount(savingsGoalDTO.getTargetAmount());
      savingsGoal.setDeadline(savingsGoalDTO.getDeadline());
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
}
