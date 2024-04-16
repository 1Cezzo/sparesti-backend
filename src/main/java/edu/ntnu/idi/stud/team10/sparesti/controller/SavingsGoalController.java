package main.java.edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import main.java.edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDTO;
import main.java.edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import main.java.edu.ntnu.idi.stud.team10.sparesti.service.SavingsGoalService;

@RestController
@RequestMapping("/api/savings-goals")
@Tag(name = "Savings Goals", description = "Operations related to savings goals")
/** Controller class for the SavingsGoal entity. */
public class SavingsGoalController {

  private final SavingsGoalService savingsGoalService;

  @Autowired
  public SavingsGoalController(SavingsGoalService savingsGoalService) {
    this.savingsGoalService = savingsGoalService;
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
    Optional<SavingsGoal> savingsGoal = savingsGoalService.getSavingsGoalById(id);
    return savingsGoal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
}
