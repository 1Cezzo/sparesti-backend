package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserSavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.service.SavingsGoalService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;
import edu.ntnu.idi.stud.team10.sparesti.util.TokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/savings-goals")
@Tag(name = "Savings Goals", description = "Operations related to savings goals")
/** Controller class for the SavingsGoal entity. */
public class SavingsGoalController {

  private final SavingsGoalService savingsGoalService;
  private final UserService userService;

  @Autowired
  public SavingsGoalController(SavingsGoalService savingsGoalService, UserService userService) {
    this.savingsGoalService = savingsGoalService;
    this.userService = userService;
  }

  /**
   * Create a new savings goal.
   *
   * @param savingsGoalDTO the DTO representing the savings goal to create
   * @return the created savings goal
   */
  @PostMapping
  @Operation(summary = "Create a new savings goal")
  public ResponseEntity<SavingsGoal> createSavingsGoal(@RequestBody SavingsGoalDto savingsGoalDTO) {
    SavingsGoal savingsGoal = savingsGoalService.createSavingsGoal(savingsGoalDTO);
    return new ResponseEntity<>(savingsGoal, HttpStatus.CREATED);
  }

  /**
   * Retrieve all savings goals.
   *
   * @return a list of all savings goals
   */
  @GetMapping
  @Operation(summary = "Get all savings goals")
  public ResponseEntity<List<SavingsGoal>> getAllSavingsGoals() {
    List<SavingsGoal> savingsGoals = savingsGoalService.getAllSavingsGoals();
    return ResponseEntity.ok(savingsGoals);
  }

  /**
   * Retrieve a savings goal by its ID.
   *
   * @param id the ID of the savings goal
   * @return the savings goal if it exists, or a 404 Not Found response otherwise
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a savings goal by its ID")
  public ResponseEntity<SavingsGoal> getSavingsGoalById(@PathVariable Long id) {
    SavingsGoal savingsGoal = savingsGoalService.getSavingsGoalById(id);
    return ResponseEntity.ok(savingsGoal);
  }

  /**
   * Update a savings goal by its ID.
   *
   * @param id the ID of the savings goal to update
   * @param savingsGoalDTO the DTO containing the updated information
   * @return the updated savings goal
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update a savings goal by its ID")
  public ResponseEntity<SavingsGoal> updateSavingsGoal(
      @PathVariable Long id, @RequestBody SavingsGoalDto savingsGoalDTO) {
    SavingsGoal updatedSavingsGoal = savingsGoalService.updateSavingsGoal(id, savingsGoalDTO);
    return ResponseEntity.ok(updatedSavingsGoal);
  }

  /**
   * Delete a savings goal by its ID.
   *
   * @param id the ID of the savings goal to delete
   * @return a 204 No Content response
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a savings goal by its ID")
  public ResponseEntity<Void> deleteSavingsGoalById(@PathVariable Long id) {
    savingsGoalService.deleteSavingsGoalById(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Update saved amount of a savings goal.
   *
   * @param user_id The ID of the savings goal.
   * @param savings_goal_id The ID of the savings goal.
   * @param savedAmount The new saved amount.
   */
  @PutMapping("/user/{user_id}/saving_goal/{savings_goal_id}/update-saved-amount")
  @Operation(summary = "Update the saved amount of a savings goal")
  public ResponseEntity<Void> updateSavedAmount(
      @PathVariable Long user_id,
      @PathVariable Long savings_goal_id,
      @RequestParam double savedAmount) {
    savingsGoalService.updateSavedAmount(user_id, savings_goal_id, savedAmount);
    return ResponseEntity.ok().build();
  }

  /**
   * Add a savings goal to a user.
   *
   * @param userEmail The email of the user to add the savings goal to.
   * @param savingsGoalId The ID of the savings goal to add.
   */
  @PostMapping("/add-user/{savingsGoalId}")
  @Operation(summary = "Add a savings goal to a user")
  public ResponseEntity<Void> addSavingsGoalToUser(
      @RequestParam("userEmail") String userEmail, @PathVariable Long savingsGoalId) {
    UserDto user = userService.getUserByEmail(userEmail);
    Long userId = user.getId();
    savingsGoalService.addSavingsGoalToUser(userId, savingsGoalId);
    return ResponseEntity.ok().build();
  }

  /**
   * Get all savings goals for a user.
   *
   * @param token The JWT access token.
   * @return A list of savings goal DTOs.
   */
  @GetMapping("/savings-goals")
  @Operation(summary = "Get all savings goals for a user")
  public ResponseEntity<List<SavingsGoalDto>> getAllSavingsGoalsForUser(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    List<SavingsGoalDto> savingsGoals = savingsGoalService.getAllSavingsGoalsForUser(userId);
    return ResponseEntity.ok(savingsGoals);
  }

  /**
   * Delete a savings goal from a user.
   *
   * @param userEmail The email of the user.
   * @param savingsGoalId The ID of the savings goal.
   */
  @DeleteMapping("/{savingsGoalId}/user/delete")
  @Operation(summary = "Delete a savings goal from a user")
  public ResponseEntity<Void> deleteSavingsGoalFromUser(
      @RequestParam("userEmail") String userEmail, @PathVariable Long savingsGoalId) {
    UserDto user = userService.getUserByEmail(userEmail);
    Long userId = user.getId();
    savingsGoalService.deleteSavingsGoalFromUser(userId, savingsGoalId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get users by saving goal.
   *
   * @param savingsGoalId The ID of the savings goal.
   */
  @GetMapping("/{savingsGoalId}/users")
  @Operation(summary = "Get users by saving goal")
  public ResponseEntity<List<UserSavingsGoalDto>> getUsersBySavingsGoal(
      @PathVariable Long savingsGoalId) {
    List<UserSavingsGoalDto> users = savingsGoalService.getUsersBySavingsGoal(savingsGoalId);
    return ResponseEntity.ok(users);
  }
}
