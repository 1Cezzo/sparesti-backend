package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserBudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static edu.ntnu.idi.stud.team10.sparesti.config.AuthorizationServerConfig.USER_ID_CLAIM;

/** Controller for Budget entities. */
@RestController
@RequestMapping("/api/budget")
@Tag(name = "Budgets", description = "Operations related to budgeting")
public class BudgetController {

  private final UserBudgetService userBudgetService;

  @Autowired
  public BudgetController(UserBudgetService userBudgetService) {
    this.userBudgetService = userBudgetService;
  }

  /**
   * Add a budget to a user.
   *
   * @param token The JWT token.
   * @param budgetDTO The budget to add.
   * @return The updated user DTO.
   */
  @PostMapping("/budgets/add")
  @Operation(summary = "Add a budget to a user")
  public ResponseEntity<UserDto> addBudgetToUser(
      @AuthenticationPrincipal Jwt token, @RequestBody BudgetDto budgetDTO) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    UserDto updatedUserDto = userBudgetService.addBudgetToUser(userId, budgetDTO);
    return ResponseEntity.ok(updatedUserDto);
  }

  /**
   * Get all budgets for a user.
   *
   * @param token The JWT token.
   * @return A list of budget DTOs.
   */
  @GetMapping("/budgets")
  @Operation(summary = "Get all budgets for a user")
  public ResponseEntity<List<BudgetDto>> getAllBudgetsForUser(@AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    List<BudgetDto> budgets = userBudgetService.getAllBudgetsForUser(userId);
    return ResponseEntity.ok(budgets);
  }

  @GetMapping("/{userId}/budgets/{budgetId}")
  @Operation(summary = "Get a budget for a user")
  public ResponseEntity<BudgetDto> getBudgetForUser(
      @PathVariable Long userId, @PathVariable Long budgetId) {
    BudgetDto budget = userBudgetService.getBudgetForUser(userId, budgetId);
    return ResponseEntity.ok(budget);
  }

  /**
   * Delete a budget from a user.
   *
   * @param token The JWT token.
   * @param budgetId The ID of the budget.
   */
  @DeleteMapping("/budgets/{budgetId}")
  @Operation(summary = "Delete a budget from a user")
  public ResponseEntity<Void> deleteBudgetFromUser(
      @AuthenticationPrincipal Jwt token, @PathVariable Long budgetId) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    userBudgetService.deleteBudgetFromUser(userId, budgetId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Add a budget row to a user's budget.
   *
   * @param token The JWT token.
   * @param budgetId The ID of the budget.
   * @param budgetRowDTO The budget row to add.
   * @return The updated budget DTO.
   */
  @PostMapping("/budgets/{budgetId}/rows/add")
  @Operation(summary = "Add a budget row to a user's budget")
  public ResponseEntity<BudgetDto> addBudgetRowToUserBudget(
      @AuthenticationPrincipal Jwt token,
      @PathVariable Long budgetId,
      @RequestBody BudgetRowDto budgetRowDTO) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    BudgetDto updatedBudgetDto =
        userBudgetService.addBudgetRowToUserBudget(userId, budgetId, budgetRowDTO);
    return ResponseEntity.ok(updatedBudgetDto);
  }

  /**
   * Delete a budget row from a user's budget.
   *
   * @param token The JWT token.
   * @param budgetId The ID of the budget.
   * @param budgetRowId The ID of the budget row.
   * @return ResponseEntity with status code.
   */
  @DeleteMapping("/budgets/{budgetId}/rows/{budgetRowId}")
  @Operation(summary = "Delete a budget row from a user's budget")
  public ResponseEntity<Void> deleteBudgetRowFromUserBudget(
      @AuthenticationPrincipal Jwt token,
      @PathVariable Long budgetId,
      @PathVariable Long budgetRowId) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    userBudgetService.deleteBudgetRowFromUserBudget(userId, budgetId, budgetRowId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Edit a budget row in a user's budget.
   *
   * @param token The JWT token.
   * @param budgetId The ID of the budget.
   * @param budgetRowId The ID of the budget row.
   * @param budgetRowDto The budget row data to update.
   * @return The updated budget row DTO.
   */
  @PutMapping("/budgets/{budgetId}/rows/{budgetRowId}")
  @Operation(summary = "Edit a budget row in a user's budget")
  public ResponseEntity<BudgetRowDto> editBudgetRowInUserBudget(
      @AuthenticationPrincipal Jwt token,
      @PathVariable Long budgetId,
      @PathVariable Long budgetRowId,
      @RequestBody BudgetRowDto budgetRowDto) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    BudgetRowDto updatedBudgetRowDto =
        userBudgetService.editBudgetRowInUserBudget(userId, budgetId, budgetRowId, budgetRowDto);
    return ResponseEntity.ok(updatedBudgetRowDto);
  }
}
