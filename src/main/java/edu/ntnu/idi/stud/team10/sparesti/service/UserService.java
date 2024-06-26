package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
import edu.ntnu.idi.stud.team10.sparesti.util.UnauthorizedException;

/** Service for User entities. */
@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final MockDataService mockDataService;
  private final BankService bankService;
  private final UserMapper userMapper;

  /**
   * Constructs a UserService with the necessary repository, password encoder, mock data service,
   * bank
   *
   * @param userRepository Repository for accessing user data.
   * @param passwordEncoder Encoder for hashing passwords.
   * @param mockDataService Service for mock data.
   * @param bankService Service for bank entities.
   * @param userMapper Mapper for converting between User and UserDto.
   */
  @Autowired
  public UserService(
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder,
      MockDataService mockDataService,
      BankService bankService,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.mockDataService = mockDataService;
    this.bankService = bankService;
    this.userMapper = userMapper;
  }

  /**
   * Adds a new User to the database.
   *
   * @param userDto (UserDto) The user to add.
   * @return A Dto representing the added user.
   * @throws IllegalArgumentException If the userDto is null.
   */
  public UserDto addUser(UserDto userDto) {
    if (userDto == null) {
      throw new IllegalArgumentException("UserDto cannot be null");
    }
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User newUser = userMapper.toEntity(userDto);
    newUser.setTotalSavings(0.0);
    return userMapper.toDto(userRepository.save(newUser));
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
    return userMapper.toDto(foundUser);
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
    Optional.ofNullable(userDto.getEmail()).ifPresent(userToUpdate::setEmail);
    Optional.ofNullable(userDto.getProfilePictureUrl())
        .ifPresent(userToUpdate::setProfilePictureUrl);
    Optional.ofNullable(userDto.getTotalSavings()).ifPresent(userToUpdate::setTotalSavings);
    Optional.ofNullable(userDto.getCheckingAccountNr())
        .ifPresent(
            accountNr -> {
              validateMockAccount(accountNr, userToUpdate.getId());
              userToUpdate.setCheckingAccountNr(accountNr);
            });
    Optional.ofNullable(userDto.getSavingsAccountNr())
        .ifPresent(
            accountNr -> {
              validateMockAccount(accountNr, userToUpdate.getId());
              userToUpdate.setSavingsAccountNr(accountNr);
            });
    return userMapper.toDto(userRepository.save(userToUpdate));
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
   * Gets a User by email.
   *
   * @param email (String) The email of the user.
   * @return A Dto representing the user.
   * @throws NotFoundException If the user is not found.
   */
  public UserDto getUserByEmail(String email) {
    User foundUser =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException("User not found"));

    return userMapper.toDto(foundUser);
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
    String role = foundUser.getRole() == null ? "USER" : foundUser.getRole();
    return org.springframework.security.core.userdetails.User.builder()
        .username(foundUser.getEmail())
        .password(foundUser.getPassword())
        .roles(role) // Can be changed to take roles from database
        .build();
  }

  /**
   * Resets a user's password.
   *
   * @param loginRequest (LoginRequestDTO) Password reset request containing username and new
   *     password.
   * @throws NotFoundException If no user with the given username is found.
   * @return true if the password was reset successfully, false otherwise.
   */
  public boolean resetPassword(LoginRequestDto loginRequest) {
    try {
      UserDto user = getUserByEmail(loginRequest.getUsername());
      String hashedPassword = passwordEncoder.encode(loginRequest.getPassword());
      user.setPassword(hashedPassword);
      userRepository.save(userMapper.toEntity(user));
      return true;
    } catch (NotFoundException e) {
      return false;
    }
  }

  /**
   * Ensures that a mock account exists, and belongs to the user.
   *
   * @param accountNr (Long) The account number to validate.
   * @param userId (Long) The id of the user.
   * @throws UnauthorizedException If the account does not exist or does not belong to the user.
   */
  private void validateMockAccount(Long accountNr, Long userId) {
    if (bankService.userHasAccessToAccount(accountNr, userId)) {
      if (!bankService.accountExists(accountNr)) {
        AccountDto account = new AccountDto();
        account.setOwnerId(userId);
        account.setAccountNr(accountNr);
        mockDataService.createMockBankAccount(account);
      }
    } else {
      throw new UnauthorizedException(
          "Account number: " + accountNr + " not found, or does not belong to the user.");
    }
  }

  /**
   * Updates the loginstreak of a user.
   *
   * @param userId (Long) The id of the user.
   */
  public void updateLoginStreak(Long userId) {
    User user = findUserById(userId);
    LocalDate lastLogin = user.getLastLogin();
    LocalDate now = LocalDate.now();

    if (lastLogin == null) {
      user.setLoginStreak(1);
    } else if (lastLogin.isEqual(now)) {
      if (user.getLoginStreak() == null) {
        user.setLoginStreak(1);
      }
    } else if (lastLogin.plusDays(1).isEqual(now)) {
      user.setLoginStreak(user.getLoginStreak() + 1);
    } else {
      user.setLoginStreak(1);
    }

    user.setLastLogin(now);
    userRepository.save(user);
  }

  /**
   * Checks if user has profile picture.
   *
   * @param userId (Long) The id of the user.
   * @throws NotFoundException If the user is not found.
   * @return (Boolean): True if the user has a profile picture, false otherwise.
   */
  public boolean hasProfilePicture(Long userId) {
    User user = findUserById(userId);
    return user.getProfilePictureUrl() != null;
  }

  /**
   * Get login streak for a user.
   *
   * @param userId (Long) The id of the user.
   * @throws NotFoundException If the user is not found.
   * @return the login streak
   */
  public Integer getLoginStreak(Long userId) {
    User user = findUserById(userId);
    return user.getLoginStreak();
  }
}
