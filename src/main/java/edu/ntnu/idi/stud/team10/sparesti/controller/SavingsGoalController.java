package edu.ntnu.idi.stud.team10.sparesti.controller;

import static edu.ntnu.idi.stud.team10.sparesti.config.AuthorizationServerConfig.USER_ID_CLAIM;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.service.SavingsGoalService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;
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
  public ResponseEntity<SavingsGoal> createSavingsGoal(@RequestBody SavingsGoalDTO savingsGoalDTO) {
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
      @PathVariable Long id, @RequestBody SavingsGoalDTO savingsGoalDTO) {
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
   * @param id The ID of the savings goal.
   * @param savedAmount The new saved amount.
   */
  @PutMapping("/{id}/update-saved-amount")
  @Operation(summary = "Update the saved amount of a savings goal")
  public ResponseEntity<Void> updateSavedAmount(
      @PathVariable Long id, @RequestParam double savedAmount) {
    savingsGoalService.updateSavedAmount(id, savedAmount);
    return ResponseEntity.ok().build();
  }

  /**
   * Add a savings goal to a user.
   *
   * @param token The JWT access token.
   * @param savingsGoalDTO The savings goal to add.
   * @return The updated user DTO.
   */
  @PostMapping("/savings-goals/add")
  @Operation(summary = "Add a savings goal to a user")
  public ResponseEntity<UserDto> addSavingsGoalToUser(
      @AuthenticationPrincipal Jwt token, @RequestBody SavingsGoalDTO savingsGoalDTO) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    UserDto updatedUserDto = savingsGoalService.addSavingsGoalToUser(userId, savingsGoalDTO);
    return ResponseEntity.ok(updatedUserDto);
  }

  /**
   * Get all savings goals for a user.
   *
   * @param token The JWT access token.
   * @return A list of savings goal DTOs.
   */
  @GetMapping("/savings-goals")
  @Operation(summary = "Get all savings goals for a user")
  public ResponseEntity<List<SavingsGoalDTO>> getAllSavingsGoalsForUser(@AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    List<SavingsGoalDTO> savingsGoals = savingsGoalService.getAllSavingsGoalsForUser(userId);
    return ResponseEntity.ok(savingsGoals);
  }

  /**
   * Delete a savings goal from a user.
   *
   * @param token The JWT access token.
   * @param savingsGoalId The ID of the savings goal.
   */
  @DeleteMapping("/savings-goals/{savingsGoalId}")
  @Operation(summary = "Delete a savings goal from a user")
  public ResponseEntity<Void> deleteSavingsGoalFromUser(
      @AuthenticationPrincipal Jwt token, @PathVariable Long savingsGoalId) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    savingsGoalService.deleteSavingsGoalFromUser(userId, savingsGoalId);
    return ResponseEntity.noContent().build();
  }
}
