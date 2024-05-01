package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserSavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.SavingsGoalMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserSavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserSavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Savings Goal entities. */
@Service
public class SavingsGoalService {

  private final SavingsGoalRepository savingsGoalRepository;
  private final UserRepository userRepository;
  private final UserSavingsGoalRepository userSavingsGoalRepository;
  private final SavingsGoalMapper savingsGoalMapper;

  @Autowired
  public SavingsGoalService(
      SavingsGoalRepository savingsGoalRepository,
      UserRepository userRepository,
      UserSavingsGoalRepository userSavingsGoalRepository,
      SavingsGoalMapper savingsGoalMapper) {
    this.savingsGoalRepository = savingsGoalRepository;
    this.userRepository = userRepository;
    this.userSavingsGoalRepository = userSavingsGoalRepository;
    this.savingsGoalMapper = savingsGoalMapper;
  }

  /**
   * Creates a new SavingsGoal entity.
   *
   * @param savingsGoalDTO the DTO representing the savings goal to create
   * @throws IllegalArgumentException if the target amount is less than or equal to 0
   * @return the created savings goal
   */
  public SavingsGoal createSavingsGoal(SavingsGoalDto savingsGoalDTO, Long userId) {
    SavingsGoal savingsGoal = savingsGoalMapper.toEntity(savingsGoalDTO);
    if (savingsGoalDTO.getTargetAmount() <= 0) {
      throw new IllegalArgumentException("Target amount must be greater than 0");
    }
    savingsGoal.setSavedAmount(0);
    savingsGoal.setCompleted(false);
    savingsGoal.setAuthorId(userId);
    // Perform validation if necessary
    return savingsGoalRepository.save(savingsGoal);
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
  public SavingsGoal updateSavingsGoal(Long id, SavingsGoalDto savingsGoalDTO) {
    SavingsGoal savingsGoal =
        savingsGoalRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Savings goal with ID " + id + " not found"));

    if (savingsGoalDTO == null) {
      throw new IllegalArgumentException("SavingsGoalDTO cannot be null");
    }

    if (savingsGoalDTO.getName() != null) {
      savingsGoal.setName(savingsGoalDTO.getName());
    }

    if (savingsGoalDTO.getTargetAmount() != 0) {
      savingsGoal.setTargetAmount(savingsGoalDTO.getTargetAmount());
    }

    if (savingsGoalDTO.getSavedAmount() != 0) {
      savingsGoal.setSavedAmount(savingsGoalDTO.getSavedAmount());
    }

    if (savingsGoalDTO.getMediaUrl() != null) {
      savingsGoal.setMediaUrl(savingsGoalDTO.getMediaUrl());
    }

    if (savingsGoalDTO.getDeadline() != null) {
      savingsGoal.setDeadline(savingsGoalDTO.getDeadline());
    }

    if (savingsGoalDTO.getAuthorId() != null) {
      savingsGoal.setAuthorId(savingsGoalDTO.getAuthorId());
    }

    LocalDateTime currentDate = LocalDateTime.now();
    savingsGoal.setCompleted(
        savingsGoal.getSavedAmount() > savingsGoal.getTargetAmount()
            || currentDate.isAfter(savingsGoal.getDeadline().atStartOfDay()));

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
      throw new IllegalArgumentException("Savings goal with ID " + id + " could not be deleted.");
    }
  }

  /**
   * Updates the saved amount of a savings goal.
   *
   * @param savingsGoalId The ID of the savings goal.
   * @param userId The ID of the user.
   * @param savedAmount The amount saved.
   */
  public void updateSavedAmount(Long userId, Long savingsGoalId, double savedAmount) {
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

    Optional<UserSavingsGoal> optionalUserSavingsGoal =
        userSavingsGoalRepository.findByUserAndSavingsGoal(user, savingsGoal);
    if (optionalUserSavingsGoal.isPresent()) {
      UserSavingsGoal userSavingsGoal = optionalUserSavingsGoal.get();
      userSavingsGoal.setContributionAmount(userSavingsGoal.getContributionAmount() + savedAmount);
      userSavingsGoal.setLastContributed(LocalDateTime.now());
      userSavingsGoalRepository.save(userSavingsGoal);

      // Check if the associated savings goal is completed
      SavingsGoal updatedSavingsGoal = userSavingsGoal.getSavingsGoal();
      updatedSavingsGoal.setSavedAmount(updatedSavingsGoal.getSavedAmount() + savedAmount);
      LocalDateTime currentDate = LocalDateTime.now();
      updatedSavingsGoal.setCompleted(
          updatedSavingsGoal.getSavedAmount() > updatedSavingsGoal.getTargetAmount()
              || currentDate.isAfter(updatedSavingsGoal.getDeadline().atStartOfDay()));
      savingsGoalRepository.save(updatedSavingsGoal);
    } else {
      throw new IllegalArgumentException(
          "User savings goal not found for user with ID "
              + userId
              + " and savings goal with ID "
              + savingsGoalId);
    }
  }

  /**
   * Adds a savings goal to a user.
   *
   * @param userId The ID of the user.
   * @param savingsGoalId The ID of the savings goal.
   * @throws NotFoundException If the user or savings goal does not exist.
   */
  public void addSavingsGoalToUser(Long userId, Long savingsGoalId) {
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

    if (savingsGoal.getTargetAmount() <= 0) {
      throw new IllegalArgumentException("Target amount must be greater than 0");
    }

    // Create a new UserSavingsGoal instance
    UserSavingsGoal userSavingsGoal = new UserSavingsGoal();
    userSavingsGoal.setUser(user);
    userSavingsGoal.setSavingsGoal(savingsGoal);
    userSavingsGoal.setContributionAmount(0);
    userSavingsGoal.setLastContributed(LocalDateTime.now());
    // Set additional attributes if needed

    // Save the userSavingsGoal entity
    userSavingsGoalRepository.save(userSavingsGoal);
  }

  /**
   * Gets all savings goals for a user.
   *
   * @param userId The ID of the user.
   * @return A list of savings goal DTOs.
   * @throws NotFoundException If the user does not exist.
   */
  public List<SavingsGoalDto> getAllSavingsGoalsForUser(Long userId) {
    List<UserSavingsGoal> userSavingsGoals = userSavingsGoalRepository.findByUserId(userId);
    return userSavingsGoals.stream()
        .map(UserSavingsGoal::getSavingsGoal)
        .map(savingsGoalMapper::toDto)
        .toList();
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

    // Find the UserSavingsGoal entity
    UserSavingsGoal userSavingsGoal =
        userSavingsGoalRepository
            .findByUserAndSavingsGoal(user, savingsGoal)
            .orElseThrow(() -> new NotFoundException("UserSavingsGoal not found"));

    // Delete the UserSavingsGoal entity
    userSavingsGoalRepository.delete(userSavingsGoal);
  }

  /**
   * Retrieves users associated with a particular savings goal along with their contribution amount
   * and last contributed date.
   *
   * @param savingsGoalId The ID of the savings goal.
   * @return A list of UserSavingsGoalDTOs.
   * @throws NotFoundException If the savings goal is not found.
   */
  public List<UserSavingsGoalDto> getUsersBySavingsGoal(Long savingsGoalId) {
    SavingsGoal savingsGoal =
        savingsGoalRepository
            .findById(savingsGoalId)
            .orElseThrow(
                () ->
                    new NotFoundException("Savings goal with ID " + savingsGoalId + " not found"));

    List<UserSavingsGoal> userSavingsGoals =
        userSavingsGoalRepository.findBySavingsGoal(savingsGoal);

    List<UserSavingsGoalDto> savingGoals =
        userSavingsGoals.stream()
            .map(
                userSavingsGoal -> {
                  User user = userSavingsGoal.getUser();
                  return new UserSavingsGoalDto(
                      user.getId(),
                      user.getEmail(),
                      user.getProfilePictureUrl(),
                      userSavingsGoal.getContributionAmount(),
                      userSavingsGoal.getLastContributed());
                })
            .toList();
    return savingGoals.stream()
        .sorted(Comparator.comparingDouble(UserSavingsGoalDto::getContributionAmount).reversed())
        .toList();
  }

  /**
   * Checks if a user has an active savings goal.
   *
   * @param userId the users Id.
   * @return {@code true} if the user has an active savings goal, {@code false} otherwise.
   */
  public boolean hasActiveSavingsGoal(Long userId) {
    List<UserSavingsGoal> userSavingsGoals = userSavingsGoalRepository.findByUserId(userId);
    for (UserSavingsGoal userSavingsGoal : userSavingsGoals) {
      SavingsGoal savingsGoal = userSavingsGoal.getSavingsGoal();
      if (!savingsGoal.isCompleted()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the current savings goal for a user.
   *
   * @param userId The ID of the user.
   * @return The current savings goal DTO.
   */
  public SavingsGoalDto getCurrentSavingsGoal(Long userId) {
    return getAllSavingsGoalsForUser(userId).stream()
        .filter(savingsGoalDto -> !savingsGoalDto.isCompleted())
        .findFirst()
        .orElseThrow(() -> new NotFoundException("No active savings goal found"));
  }
}
