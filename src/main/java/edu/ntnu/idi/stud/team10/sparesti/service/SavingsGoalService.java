package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Savings Goal entities. */
@Service
public class SavingsGoalService {

  private final SavingsGoalRepository savingsGoalRepository;
  private final UserRepository userRepository;

  @Autowired
  public SavingsGoalService(
      SavingsGoalRepository savingsGoalRepository, UserRepository userRepository) {
    this.savingsGoalRepository = savingsGoalRepository;
    this.userRepository = userRepository;
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
   * @return the savings goal if it exists.
   * @throws NotFoundException if the savings goal is not found
   */
  public SavingsGoal getSavingsGoalById(Long id) {
    return savingsGoalRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Savings goal with ID " + id + " not found"));
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
    SavingsGoal savingsGoal =
        savingsGoalRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Savings goal with ID " + id + " not found"));
    if (savingsGoalDTO == null) {
      throw new IllegalArgumentException("SavingsGoalDTO cannot be null");
    }
    if (savingsGoalDTO.getTargetAmount() <= 0) {
      throw new IllegalArgumentException("Target amount must be greater than 0");
    }
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

  /**
   * Adds a savings goal to a user.
   *
   * @param userId The ID of the user.
   * @param savingsGoalDto The DTO representing the savings goal.
   * @return
   * @throws NotFoundException If the user does not exist or target amount is less than or equal to
   *     0.
   */
  public UserDto addSavingsGoalToUser(Long userId, SavingsGoalDTO savingsGoalDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    SavingsGoal savingsGoal = savingsGoalDto.toEntity();
    if (savingsGoal.getTargetAmount() <= 0) {
      throw new IllegalArgumentException("Target amount must be greater than 0");
    }
    savingsGoal.setSavedAmount(0);
    savingsGoal.setCompleted(false);
    savingsGoal.setUser(user);
    savingsGoalRepository.save(savingsGoal);
    return new UserDto(user);
  }

  /**
   * Gets all savings goals for a user.
   *
   * @param userId The ID of the user.
   * @return A list of savings goal DTOs.
   * @throws NotFoundException If the user does not exist.
   */
  public List<SavingsGoalDTO> getAllSavingsGoalsForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    return savingsGoalRepository.findByUser(user).stream()
        .map(SavingsGoalDTO::new)
        .collect(Collectors.toList());
  }

  /**
   * Deletes a savings goal from a user.
   *
   * @param userId The ID of the user.
   * @param savingsGoalId The ID of the savings goal.
   * @throws NotFoundException If the user or savings goal does not exist.
   */
  public void deleteSavingsGoalFromUser(Long userId, Long savingsGoalId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    SavingsGoal savingsGoal =
        savingsGoalRepository
            .findById(savingsGoalId)
            .orElseThrow(
                () ->
                    new NotFoundException("Savings goal with ID " + savingsGoalId + " not found"));
  }
}
