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
import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ExistingUserException;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for User entities. */
@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SavingsGoalRepository savingsGoalRepository;
  private final BudgetRepository budgetRepository;
  private final BudgetRowRepository budgetRowRepository;
  private final BadgeRepository badgeRepository;
  private final ChallengeRepository challengeRepository;
  private BankService bankService;

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
      ChallengeRepository challengeRepository,
      BudgetRepository budgetRepository,
      BudgetRowRepository budgetRowRepository,
      BadgeRepository badgeRepository,
      BankService bankService) {
    this.userRepository = userRepository;
    this.budgetRepository = budgetRepository;
    this.budgetRowRepository = budgetRowRepository;
    this.savingsGoalRepository = savingsGoalRepository;
    this.challengeRepository = challengeRepository;
    this.badgeRepository = badgeRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.bankService = bankService;
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
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User newUser = new User(userDto);
    return new UserDto(userRepository.save(newUser));
  }

  /**
   * Sets the display name of a user.
   *
   * @param dto (UserDto) The user to update.
   * @return A Dto representing the updated user.
   * @throws NotFoundException If the user is not found.
   */
  public UserDto setDisplayName(UserDto dto) {
    User user = findUserById(dto.getId());
    user.setDisplayName(dto.getDisplayName());
    return new UserDto(userRepository.save(user));
  }

  /**
   * Gets a User by id.
   *
   * @param id (Long) The unique id of the user.
   * @return A Dto representing the user.
   * @throws NotFoundException If the user is not found.
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
   * @throws NotFoundException If the user is not found.
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
   * @throws NotFoundException If the user does not exist.
   */
  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new NotFoundException("User with id " + id + " not found");
    }
    userRepository.deleteById(id);
  }

  /**
   * Gets a User by username.
   *
   * @param username (String) The unique username of the user.
   * @return A Dto representing the user.
   * @throws NotFoundException If the user is not found.
   */
  public UserDto getUserByUsername(String username) {
    User foundUser = findUserByDisplayName(username);

    return new UserDto(foundUser);
  }

  /**
   * Finds a User by id, if it exists.
   *
   * @param id (Long) The unique id of the user.
   * @return The User, if found.
   * @throws NotFoundException If the user is not found.
   */
  private User findUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
  }

  /**
   * Finds a User by display name, if it exists.
   *
   * @param displayName (String) The display name of the user.
   * @return The User, if found.
   * @throws NotFoundException If the user is not found.
   */
  private User findUserByDisplayName(String displayName) {
    return userRepository
        .findByDisplayName(displayName)
        .orElseThrow(
            () -> new NotFoundException("User with display name " + displayName + " not found"));
  }

  /**
   * Overridden method from Spring Security. Loads a user by a username, which in this application
   * is the email of the user.
   *
   * @param username (String) The email of the user.
   * @return The UserDetails of the user.
   * @throws UsernameNotFoundException If the user is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User foundUser =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return org.springframework.security.core.userdetails.User.builder()
        .username(foundUser.getEmail())
        .password(foundUser.getPassword())
        .roles("USER") // Can be changed to take roles from database
        .build();
  }

  /**
   * Adds a budget to a user.
   *
   * @param userId the user id to add the budget for.
   * @param budgetDto the budget to add.
   * @return the updated user.
   */
  public UserDto addBudgetToUser(Long userId, BudgetDto budgetDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget = budgetDto.toEntity();
    budget.setUser(user);
    budgetRepository.save(budget);
    return new UserDto(user);
  }

  /**
   * Gets all budgets for a user.
   *
   * @param userId the user id to get budgets for.
   * @return a list of budget DTOs.
   */
  public List<BudgetDto> getAllBudgetsForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    return budgetRepository.findByUser(user).stream()
        .map(BudgetDto::new)
        .collect(Collectors.toList());
  }

  /**
   * Deletes a budget from a user.
   *
   * @param userId the user id to delete the budget from.
   * @param budgetId the budget id to delete.
   */
  public void deleteBudgetFromUser(Long userId, Long budgetId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    budgetRepository.delete(budget);
  }

  /**
   * Adds a budget row to a user's budget.
   *
   * @param userId the user id to add the budget row for.
   * @param budgetId the budget id to add the budget row for.
   * @param budgetRowDto the budget row to add.
   * @return the updated budget.
   */
  public BudgetDto addBudgetRowToUserBudget(Long userId, Long budgetId, BudgetRowDto budgetRowDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow = budgetRowDto.toEntity();
    budgetRow.setBudget(budget);
    budget.getRow().add(budgetRow);
    budgetRepository.save(budget);

    return new BudgetDto(budget);
  }

  /**
   * Deletes a budget row from a user's budget.
   *
   * @param userId the user id to delete the budget row for.
   * @param budgetId the budget id to delete the budget row for.
   * @param budgetRowId the budget row id to delete.
   */
  public void deleteBudgetRowFromUserBudget(Long userId, Long budgetId, Long budgetRowId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow =
        budgetRowRepository
            .findById(budgetRowId)
            .orElseThrow(
                () -> new NotFoundException("BudgetRow with ID " + budgetRowId + " not found"));

    budget.getRow().remove(budgetRow);
    budgetRepository.save(budget);
  }

  /**
   * Edits a budget row in a user's budget.
   *
   * @param userId the user id to edit the budget row for.
   * @param budgetId the budget id to edit the budget row for.
   * @param budgetRowId the budget row id to edit.
   * @param budgetRowDto the new data for the budget row.
   * @return the updated budget row.
   */
  public BudgetRowDto editBudgetRowInUserBudget(
      Long userId, Long budgetId, Long budgetRowId, BudgetRowDto budgetRowDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow =
        budgetRowRepository
            .findById(budgetRowId)
            .orElseThrow(
                () -> new NotFoundException("BudgetRow with ID " + budgetRowId + " not found"));

    // Update the budget row with the new data
    budgetRow.updateFromDto(budgetRowDto);
    budgetRowRepository.save(budgetRow);

    return new BudgetRowDto(budgetRow);
  }

  /**
   * Returns a Set of all the badges earned by a user, as DTOs.
   *
   * @param userId (Long): The User's unique ID.
   * @return A Set of all Badges that a User has earned, in DTO form.
   */
  public Set<BadgeDto> getAllBadgesByUserId(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    Set<BadgeDto> badges =
        user.getEarnedBadges().stream().map(BadgeDto::new).collect(Collectors.toSet());
    return badges;
  }

  /**
   * Awards a Badge of badgeId to a User of userId
   *
   * @param userId (Long): The User's id (who is earning the Badge)
   * @param badgeId (Long): The Badge's id (the Badge being awarded)
   * @throws NotFoundException If either the User or Badge id is not found.
   */
  @Transactional
  public void giveUserBadge(Long userId, Long badgeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new NotFoundException("Badge with ID " + badgeId + " not found."));
    user.addBadge(badge);
    badge.addUser(user);
    userRepository.save(user);
    badgeRepository.save(badge);
  }

  /**
   * Removes a user's badge, given the User and Badge id's.
   *
   * @param userId (Long): The User's id (who is losing the Badge)
   * @param badgeId (Long): The Badge's id (the Badge being removed)
   * @throws NotFoundException If either the User or Badge id is not found.
   */
  @Transactional
  public void removeUserBadge(Long userId, Long badgeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new NotFoundException("Badge with ID " + badgeId + " not found."));
    user.removeBadge(badge);
    badge.removeUser(user);
    userRepository.save(user);
  }

  /**
   * Retrieves all users that have acquired a certain badge
   *
   * @param badgeId (Long): The badge id being researched.
   * @return an ArrayList of Users that have earned the badge.
   * @throws NotFoundException When the badge id is not found in the database.
   */
  public List<User> getUsersByBadge(Long badgeId) {
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new NotFoundException("Badge with ID " + badgeId + " not found."));
    return new ArrayList<>(badge.getUsers()); // possible null exception
  }

  /**
   * Creates a new mock account and adds it to the user.
   *
   * @param displayName (String): The username of the user the account is being added to
   * @param accountNr (Integer): The account number being created
   * @param isSavingsAcc (boolean): Whether the account is going to be the savings account
   *                     (if false; will be checking account)
   * @return the AccountDto
   */
  @Transactional
  public AccountDto addMockBankAccount(String displayName, Integer accountNr, boolean isSavingsAcc) {
    // Bad and can be removed/altered in any way, but should work for mock data.
    // Protected for now
    User mockUser = findUserByDisplayName(displayName);
    AccountDto accountDto = new AccountDto();
    accountDto.setAccountNr(accountNr);
    accountDto.setName(mockUser.getDisplayName());
    accountDto.setOwnerId(mockUser.getId());

    if (isSavingsAcc) {
      accountDto.setName(accountDto.getName() + "'s savings account");
      mockUser.setSavingsAccountNr(accountNr);
    } else {
      accountDto.setName(accountDto.getName() + "'s checking account");
      mockUser.setCheckingAccountNr(accountNr);
    }

    userRepository.save(mockUser);
    return bankService.createAccount(accountDto);
  }
}
