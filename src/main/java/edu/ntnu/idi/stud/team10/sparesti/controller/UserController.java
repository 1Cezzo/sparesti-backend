package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.ChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/** Controller for handling user requests. */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "User", description = "Operations related to creating and deleting a user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Get a user by username.
   *
   * @param username the username of the user
   * @return the user with the given username
   */
  @GetMapping("/{username}")
  @Operation(summary = "Access the user data")
  public UserDto getUserByUsername(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

  /**
   * Create a new user.
   *
   * @param userDTO the user to create
   * @return the response entity
   */
  @PostMapping("/create")
  @Operation(summary = "Create a new user")
  public UserDto createUser(@RequestBody UserDto userDTO) {
    return userService.addUser(userDTO);
  }

  /**
   * Delete a user by id.
   *
   * @param id The id of the user to delete.
   * @return ResponseEntity with status code.
   */
  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete a user")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Add a savings goal to a user.
   *
   * @param userId The ID of the user.
   * @param savingsGoalDTO The savings goal to add.
   * @return The updated user DTO.
   */
  @PostMapping("/{userId}/savings-goals/add")
  @Operation(summary = "Add a savings goal to a user")
  public ResponseEntity<String> addSavingsGoalToUser(
      @PathVariable Long userId, @RequestBody SavingsGoalDTO savingsGoalDTO) {
    try {
      UserDto updatedUserDto = userService.addSavingsGoalToUser(userId, savingsGoalDTO);
      return ResponseEntity.ok("Saving goal created and added to user"); // Return 200 OK status
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage()); // Return 400 Bad Request status
    }
  }

  /**
   * Get all savings goals for a user.
   *
   * @param userId The ID of the user.
   * @return A list of savings goal DTOs.
   */
  @GetMapping("/{userId}/savings-goals")
  @Operation(summary = "Get all savings goals for a user")
  public ResponseEntity<List<SavingsGoalDTO>> getAllSavingsGoalsForUser(@PathVariable Long userId) {
    List<SavingsGoalDTO> savingsGoals = userService.getAllSavingsGoalsForUser(userId);
    return ResponseEntity.ok(savingsGoals);
  }

  /**
   * Delete a savings goal from a user.
   *
   * @param userId The ID of the user.
   * @param savingsGoalId The ID of the savings goal.
   */
  @DeleteMapping("/{userId}/savings-goals/{savingsGoalId}")
  @Operation(summary = "Delete a savings goal from a user")
  public ResponseEntity<Void> deleteSavingsGoalFromUser(
      @PathVariable Long userId, @PathVariable Long savingsGoalId) {
    userService.deleteSavingsGoalFromUser(userId, savingsGoalId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{userId}/challenges/add")
  @Operation(summary = "Add a challenge to a user")
  public ResponseEntity<UserDto> addChallengeToUser(
      @PathVariable Long userId, @RequestParam Long challengeId) {
    try {
      UserDto updatedUserDto = userService.addChallengeToUser(userId, challengeId);
      return ResponseEntity.ok(updatedUserDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{userId}/challenges/{challengeId}")
  @Operation(summary = "Remove a challenge from a user")
  public ResponseEntity<UserDto> removeChallengeFromUser(
      @PathVariable Long userId, @PathVariable Long challengeId) {
    try {
      UserDto updatedUserDto = userService.removeChallengeFromUser(userId, challengeId);
      return ResponseEntity.ok(updatedUserDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @GetMapping("/{userId}/challenges")
  @Operation(summary = "Get all challenges for a user")
  public ResponseEntity<Map<String, List<? extends ChallengeDTO>>> getChallengesByUser(
      @PathVariable Long userId) {
    try {
      Map<String, List<? extends ChallengeDTO>> challengesMap =
          userService.getChallengesByUser(userId);
      return ResponseEntity.ok(challengesMap);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
}
