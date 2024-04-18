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
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ExistingUserException;
import edu.ntnu.idi.stud.team10.sparesti.util.InvalidIdException;

/** Service for User entities. */
@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SavingsGoalRepository savingsGoalRepository;
  private final ChallengeRepository challengeRepository;

  /**
   * Constructor for UserService, with automatic injection of dependencies.
   *
   * @param userRepository (UserRepository) The repository for User entities.
   * @param savingsGoalRepository (SavingsGoalRepository) The repository for SavingsGoal entities.
   * @param challengeRepository (ChallengeRepository) The repository for Challenge entities.
   */
  @Autowired
  public UserService(
      UserRepository userRepository,
      SavingsGoalRepository savingsGoalRepository,
      ChallengeRepository challengeRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.savingsGoalRepository = savingsGoalRepository;
    this.challengeRepository = challengeRepository;
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

  @Transactional
  public UserDto addChallengeToUser(Long userId, Long challengeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    Challenge challenge =
        challengeRepository
            .findById(challengeId)
            .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

    user.addChallenge(challenge);
    userRepository.save(user);

    return new UserDto(user);
  }

  @Transactional
  public UserDto removeChallengeFromUser(Long userId, Long challengeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    Challenge challengeToRemove =
        user.getChallenges().stream()
            .filter(challenge -> challenge.getId().equals(challengeId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

    user.removeChallenge(challengeToRemove);
    userRepository.save(user);
    challengeRepository.delete(challengeToRemove); // Delete the challenge from the database
    return new UserDto(user);
  }

  private List<ConsumptionChallengeDTO> fetchConsumptionChallengesForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return user.getChallenges().stream()
        .filter(challenge -> challenge instanceof ConsumptionChallenge)
        .map(ConsumptionChallengeDTO::new)
        .collect(Collectors.toList());
  }

  private List<PurchaseChallengeDTO> fetchPurchaseChallengesForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return user.getChallenges().stream()
        .filter(challenge -> challenge instanceof PurchaseChallenge)
        .map(PurchaseChallengeDTO::new)
        .collect(Collectors.toList());
  }

  private List<SavingChallengeDTO> fetchSavingChallengesForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return user.getChallenges().stream()
        .filter(challenge -> challenge instanceof SavingChallenge)
        .map(SavingChallengeDTO::new)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Map<String, List<? extends ChallengeDTO>> getChallengesByUser(Long userId) {
    Map<String, List<? extends ChallengeDTO>> challengesMap = new HashMap<>();

    // Fetch challenges for the user
    List<ConsumptionChallengeDTO> consumptionChallenges = fetchConsumptionChallengesForUser(userId);
    List<PurchaseChallengeDTO> purchaseChallenges = fetchPurchaseChallengesForUser(userId);
    List<SavingChallengeDTO> savingChallenges = fetchSavingChallengesForUser(userId);

    // Add lists to the map
    challengesMap.put("consumptionChallenges", consumptionChallenges);
    challengesMap.put("purchaseChallenges", purchaseChallenges);
    challengesMap.put("savingChallenges", savingChallenges);

    return challengesMap;
  }
}
