package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ExistingUserException;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for User entities. */
@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final BudgetRepository budgetRepository;
  private final BudgetRowRepository budgetRowRepository;
  private final BadgeRepository badgeRepository;

  /**
   * Constructor for UserService, with automatic injection of dependencies.
   *
   * @param userRepository (UserRepository) The repository for User entities.
   */
  @Autowired
  public UserService(
      UserRepository userRepository,
      BudgetRepository budgetRepository,
      BudgetRowRepository budgetRowRepository,
      BadgeRepository badgeRepository) {
    this.userRepository = userRepository;
    this.budgetRepository = budgetRepository;
    this.budgetRowRepository = budgetRowRepository;
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
}
