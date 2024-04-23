package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserResponseDto;
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
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDTO));
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
    UserDto updatedUserDto = userService.addBudgetToUser(userId, budgetDTO);
    return ResponseEntity.ok(updatedUserDto);
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
    BudgetDto updatedBudgetDto =
        userService.addBudgetRowToUserBudget(userId, budgetId, budgetRowDTO);
    return ResponseEntity.ok(updatedBudgetDto);
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
    BudgetRowDto updatedBudgetRowDto =
        userService.editBudgetRowInUserBudget(userId, budgetId, budgetRowId, budgetRowDto);
    return ResponseEntity.ok(updatedBudgetRowDto);
  }


  @PostMapping("/mock/account/add")
  @Operation(summary = "Add a mock account to a user")
  public ResponseEntity<?> addMockAccountToUser(@RequestParam String displayName,
                                                @RequestParam boolean isSavingsAccount,
                                                @RequestParam Integer accountNr) {
    userService.addMockBankAccount(displayName, accountNr, isSavingsAccount);
    return ResponseEntity.ok().body("Account added successfully");
  }

  /**
   * Get a user's details.
   *
   * @param displayName The username of the user.
   * @return The user's details.
   */
  @GetMapping("/info/{displayName}")
  @Operation(summary = "Get a user's details")
  public ResponseEntity<UserResponseDto> getUserDetails(@PathVariable String displayName) {
    UserResponseDto userResponse = userService.getUserDetails(displayName);
    return ResponseEntity.ok(userResponse);
  }
}
