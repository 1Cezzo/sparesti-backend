package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ExistingUserException;
import edu.ntnu.idi.stud.team10.sparesti.util.InvalidIdException;
import jakarta.transaction.Transactional;

/** Service for User entities. */
@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SavingsGoalRepository savingsGoalRepository;
  private final BudgetRepository budgetRepository;
  private final BudgetRowRepository budgetRowRepository;
  private final BadgeRepository badgeRepository;

  /**
   * Constructor for UserService, with automatic injection of dependencies.
   *
   * @param userRepository (UserRepository) The repository for User entities.
   * @param savingsGoalRepository (SavingsGoalRepository) The repository for SavingsGoal entities.
   */
  @Autowired
  public UserService(
      UserRepository userRepository,
      BudgetRepository budgetRepository,
      BudgetRowRepository budgetRowRepository,
      SavingsGoalRepository savingsGoalRepository,
      BadgeRepository badgeRepository) {
    this.userRepository = userRepository;
    this.budgetRepository = budgetRepository;
    this.budgetRowRepository = budgetRowRepository;
    this.savingsGoalRepository = savingsGoalRepository;
    this.badgeRepository = badgeRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  /**
   * Adds a new User to the database.
   *
   * @param userDto (UserDto) The user to add.
   * @return A Dto representing the added user.
   * @throws ExistingUserException If the username already exists.
   * @throws IllegalArgumentException If the userDto is null.
   */
  public UserDto addUser(UserDto userDto) {
    if (userDto == null) {
      throw new IllegalArgumentException("UserDto cannot be null");
    }
    if (userRepository.existsByUsername(userDto.getUsername())) {
      throw new ExistingUserException("Username already exists.");
    }
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User newUser = new User(userDto);
    return new UserDto(userRepository.save(newUser));
  }

  /**
   * Gets a User by id.
   *
   * @param id (Long) The unique id of the user.
   * @return A Dto representing the user.
   * @throws InvalidIdException If the user is not found.
   */
  public UserDto getUserById(Long id) {
    User foundUser = findUserById(id);
    return new UserDto(foundUser);
  }

  /**
   * Updates an existing User by its id.
   *
   * @param userDto (UserDto) The user to update.
   * @return A Dto representing the updated user.
   * @throws InvalidIdException If the user is not found.
   * @throws IllegalArgumentException If the userDto is null.
   */
  public UserDto updateUser(UserDto userDto) {
    if (userDto == null) {
      throw new IllegalArgumentException("UserDto cannot be null");
    }
    User userToUpdate = findUserById(userDto.getId());

    // Update all non-null fields included in the request.
    Optional.ofNullable(userDto.getPassword()).ifPresent(userToUpdate::setPassword);
    Optional.ofNullable(userDto.getEmail()).ifPresent(userToUpdate::setEmail);
    Optional.ofNullable(userDto.getProfilePictureUrl())
        .ifPresent(userToUpdate::setProfilePictureUrl);

    return new UserDto(userRepository.save(userToUpdate));
  }

  /**
   * Deletes a User by id.
   *
   * @param id (Long) The unique id of the user.
   * @throws InvalidIdException If the user does not exist.
   */
  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new InvalidIdException("User with id " + id + " not found");
    }
    userRepository.deleteById(id);
  }

  /**
   * Gets a User by username.
   *
   * @param username (String) The unique username of the user.
   * @return A Dto representing the user.
   * @throws InvalidIdException If the user is not found.
   */
  public UserDto getUserByUsername(String username) {
    User foundUser = findUserByUsername(username);

    return new UserDto(foundUser);
  }

  /**
   * Finds a User by id, if it exists.
   *
   * @param id (Long) The unique id of the user.
   * @return The User, if found.
   * @throws InvalidIdException If the user is not found.
   */
  private User findUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new InvalidIdException("User with id " + id + " not found"));
  }

  /**
   * Finds a User by username, if it exists.
   *
   * @param username (String) The username of the user.
   * @return The User, if found.
   * @throws InvalidIdException If the user is not found.
   */
  private User findUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new InvalidIdException("User with username " + username + " not found"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User foundUser = findUserByUsername(username);
    return org.springframework.security.core.userdetails.User.builder()
        .username(foundUser.getUsername())
        .password(foundUser.getPassword())
        .roles("USER") // Can be changed to take roles from database
        .build();
  }

  /**
   * Adds a savings goal to a user.
   *
   * @param userId The ID of the user.
   * @param savingsGoalDto The DTO representing the savings goal.
   * @return
   * @throws InvalidIdException If the user does not exist or target amount is less than or equal to
   *     0.
   */
  public UserDto addSavingsGoalToUser(Long userId, SavingsGoalDTO savingsGoalDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

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
   * @throws InvalidIdException If the user does not exist.
   */
  public List<SavingsGoalDTO> getAllSavingsGoalsForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    return savingsGoalRepository.findByUser(user).stream()
        .map(SavingsGoalDTO::new)
        .collect(Collectors.toList());
  }

  /**
   * Deletes a savings goal from a user.
   *
   * @param userId The ID of the user.
   * @param savingsGoalId The ID of the savings goal.
   * @throws InvalidIdException If the user or savings goal does not exist.
   */
  public void deleteSavingsGoalFromUser(Long userId, Long savingsGoalId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    SavingsGoal savingsGoal =
        savingsGoalRepository
            .findById(savingsGoalId)
            .orElseThrow(
                () ->
                    new InvalidIdException("Savings goal with ID " + savingsGoalId + " not found"));
  }

  public UserDto addBudgetToUser(Long userId, BudgetDto budgetDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    Budget budget = budgetDto.toEntity();
    budget.setUser(user);
    budgetRepository.save(budget);
    return new UserDto(user);
  }

  public List<BudgetDto> getAllBudgetsForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    return budgetRepository.findByUser(user).stream()
        .map(BudgetDto::new)
        .collect(Collectors.toList());
  }

  public void deleteBudgetFromUser(Long userId, Long budgetId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new InvalidIdException("Budget with ID " + budgetId + " not found"));

    budgetRepository.delete(budget);
  }

  public BudgetDto addBudgetRowToUserBudget(Long userId, Long budgetId, BudgetRowDto budgetRowDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new InvalidIdException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow = budgetRowDto.toEntity();
    budgetRow.setBudget(budget);
    budget.getRow().add(budgetRow);
    budgetRepository.save(budget);

    return new BudgetDto(budget);
  }

  public void deleteBudgetRowFromUserBudget(Long userId, Long budgetId, Long budgetRowId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new InvalidIdException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow =
        budgetRowRepository
            .findById(budgetRowId)
            .orElseThrow(
                () -> new InvalidIdException("BudgetRow with ID " + budgetRowId + " not found"));

    budget.getRow().remove(budgetRow);
    budgetRepository.save(budget);
  }

  public BudgetRowDto editBudgetRowInUserBudget(
      Long userId, Long budgetId, Long budgetRowId, BudgetRowDto budgetRowDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new InvalidIdException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow =
        budgetRowRepository
            .findById(budgetRowId)
            .orElseThrow(
                () -> new InvalidIdException("BudgetRow with ID " + budgetRowId + " not found"));

    // Update the budget row with the new data
    budgetRow.updateFromDto(budgetRowDto);
    budgetRowRepository.save(budgetRow);

    return new BudgetRowDto(budgetRow);
  }

  /**
   * Returns a Set of all the badges earned by a user.
   *
   * @param userId (Long): The User's unique ID.
   * @return A Set of all Badges that a User has earned.
   */
  public Set<Badge> getAllBadgesByUserId(
      Long userId) { // @Transactional readonly attribute may be needed?
    return userRepository
        .findById(userId)
        .map(User::getEarnedBadges)
        .orElse(Collections.emptySet());
  }

  /**
   * Awards a Badge of badgeId to a User of userId
   *
   * @param userId (Long): The User's id (who is earning the Badge)
   * @param badgeId (Long): The Badge's id (the Badge being awarded)
   * @throws InvalidIdException If either the User or Badge id is not found.
   */
  @Transactional
  public void giveUserBadge(Long userId, Long badgeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new InvalidIdException("Badge with ID " + badgeId + " not found."));
    user.getEarnedBadges().add(badge);
    userRepository.save(user);
  }

  /**
   * Removes a user's badge, given the User and Badge id's.
   *
   * @param userId (Long): The User's id (who is losing the Badge)
   * @param badgeId (Long): The Badge's id (the Badge being removed)
   * @throws InvalidIdException If either the User or Badge id is not found.
   */
  @Transactional
  public void removeUserBadge(Long userId, Long badgeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidIdException("User with ID " + userId + " not found"));
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new InvalidIdException("Badge with ID " + badgeId + " not found."));
    if (user.getEarnedBadges().remove(badge)) {
      userRepository.save(user);
    }
  }

  /**
   * Retrieves all users that have acquired a certain badge
   *
   * @param badgeId (Long): The badge id being researched.
   * @return an ArrayList of Users that have earned the badge.
   * @throws InvalidIdException When the badge id is not found in the database.
   */
  public List<User> getUsersByBadge(Long badgeId) {
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new InvalidIdException("Badge with ID " + badgeId + " not found."));
    return new ArrayList<>(badge.getUsers()); // possible null exception
  }
}
