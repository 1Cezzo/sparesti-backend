package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserBudgetService;
import edu.ntnu.idi.stud.team10.sparesti.util.TokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
  public ResponseEntity<BudgetDto> addBudgetToUser(
      @AuthenticationPrincipal Jwt token, @RequestBody BudgetDto budgetDTO) {
    Long userId = TokenParser.extractUserId(token);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userBudgetService.addBudgetToUser(userId, budgetDTO));
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
    Long userId = TokenParser.extractUserId(token);
    List<BudgetDto> budgets = userBudgetService.getAllBudgetsForUser(userId);
    return ResponseEntity.ok(budgets);
  }

  @GetMapping("/budgets/{budgetId}")
  @Operation(summary = "Get a budget for a user")
  public ResponseEntity<BudgetDto> getBudgetForUser(
      @AuthenticationPrincipal Jwt token, @PathVariable Long budgetId) {
    Long userId = TokenParser.extractUserId(token);
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
    Long userId = TokenParser.extractUserId(token);
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
    Long userId = TokenParser.extractUserId(token);
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
    Long userId = TokenParser.extractUserId(token);
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
    Long userId = TokenParser.extractUserId(token);
    BudgetRowDto updatedBudgetRowDto =
        userBudgetService.editBudgetRowInUserBudget(userId, budgetId, budgetRowId, budgetRowDto);
    return ResponseEntity.ok(updatedBudgetRowDto);
  }

  @GetMapping("/budgets/getnew")
  @Operation(summary = "Get the newest budget")
  public ResponseEntity<BudgetDto> getNewestBudget(@AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    Optional<BudgetDto> budget = userBudgetService.getNewestBudget(userId);
    return budget.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(null));
  }
}
