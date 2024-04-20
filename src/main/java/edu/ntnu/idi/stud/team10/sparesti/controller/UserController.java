package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
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
  @Operation(summary = "Create a new savings goal")
  public ResponseEntity<String> createUser(@RequestBody UserDto userDTO) {
    try {
      userService.addUser(userDTO);
      return ResponseEntity.ok("User created successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
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
   * Add a budget to a user.
   *
   * @param userId The ID of the user.
   * @param budgetDTO The budget to add.
   * @return The updated user DTO.
   */
  @PostMapping("/{userId}/budgets/add")
  @Operation(summary = "Add a budget to a user")
  public ResponseEntity<UserDto> addBudgetToUser(
      @PathVariable Long userId, @RequestBody BudgetDto budgetDTO) {
    try {
      UserDto updatedUserDto = userService.addBudgetToUser(userId, budgetDTO);
      return ResponseEntity.ok(updatedUserDto); // Return 200 OK status
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build(); // Return 400 Bad Request status
    }
  }

  /**
   * Get all budgets for a user.
   *
   * @param userId The ID of the user.
   * @return A list of budget DTOs.
   */
  @GetMapping("/{userId}/budgets")
  @Operation(summary = "Get all budgets for a user")
  public ResponseEntity<List<BudgetDto>> getAllBudgetsForUser(@PathVariable Long userId) {
    List<BudgetDto> budgets = userService.getAllBudgetsForUser(userId);
    return ResponseEntity.ok(budgets);
  }

  /**
   * Delete a budget from a user.
   *
   * @param userId The ID of the user.
   * @param budgetId The ID of the budget.
   */
  @DeleteMapping("/{userId}/budgets/{budgetId}")
  @Operation(summary = "Delete a budget from a user")
  public ResponseEntity<Void> deleteBudgetFromUser(
      @PathVariable Long userId, @PathVariable Long budgetId) {
    userService.deleteBudgetFromUser(userId, budgetId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Add a budget row to a user's budget.
   *
   * @param userId The ID of the user.
   * @param budgetId The ID of the budget.
   * @param budgetRowDTO The budget row to add.
   * @return The updated budget DTO.
   */
  @PostMapping("/{userId}/budgets/{budgetId}/rows/add")
  @Operation(summary = "Add a budget row to a user's budget")
  public ResponseEntity<BudgetDto> addBudgetRowToUserBudget(
      @PathVariable Long userId,
      @PathVariable Long budgetId,
      @RequestBody BudgetRowDto budgetRowDTO) {
    try {
      BudgetDto updatedBudgetDto =
          userService.addBudgetRowToUserBudget(userId, budgetId, budgetRowDTO);
      return ResponseEntity.ok(updatedBudgetDto); // Return 200 OK status
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build(); // Return 400 Bad Request status
    }
  }

  /**
   * Delete a budget row from a user's budget.
   *
   * @param userId The ID of the user.
   * @param budgetId The ID of the budget.
   * @param budgetRowId The ID of the budget row.
   * @return ResponseEntity with status code.
   */
  @DeleteMapping("/{userId}/budgets/{budgetId}/rows/{budgetRowId}")
  @Operation(summary = "Delete a budget row from a user's budget")
  public ResponseEntity<Void> deleteBudgetRowFromUserBudget(
      @PathVariable Long userId, @PathVariable Long budgetId, @PathVariable Long budgetRowId) {
    userService.deleteBudgetRowFromUserBudget(userId, budgetId, budgetRowId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Edit a budget row in a user's budget.
   *
   * @param userId The ID of the user.
   * @param budgetId The ID of the budget.
   * @param budgetRowId The ID of the budget row.
   * @param budgetRowDto The budget row data to update.
   * @return The updated budget row DTO.
   */
  @PutMapping("/{userId}/budgets/{budgetId}/rows/{budgetRowId}")
  @Operation(summary = "Edit a budget row in a user's budget")
  public ResponseEntity<BudgetRowDto> editBudgetRowInUserBudget(
      @PathVariable Long userId,
      @PathVariable Long budgetId,
      @PathVariable Long budgetRowId,
      @RequestBody BudgetRowDto budgetRowDto) {
    try {
      BudgetRowDto updatedBudgetRowDto =
          userService.editBudgetRowInUserBudget(userId, budgetId, budgetRowId, budgetRowDto);
      return ResponseEntity.ok(updatedBudgetRowDto); // Return 200 OK status
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build(); // Return 400 Bad Request status
    }
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
}
