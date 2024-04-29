package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.SavingChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/saving-challenges")
@Tag(name = "Saving Challenges", description = "Endpoints for handling saving challenges.")
/** Controller for handling saving challenges. */
public class SavingChallengeController {

  private final SavingChallengeService savingChallengeService;

  @Autowired
  public SavingChallengeController(SavingChallengeService savingChallengeService) {
    this.savingChallengeService = savingChallengeService;
  }

  /**
   * Create a new saving challenge.
   *
   * @param dto The DTO representing the saving challenge to create
   * @return The created saving challenge
   */
  @PostMapping("/saving")
  @Operation(summary = "Create a new saving challenge")
  public ResponseEntity<SavingChallenge> createSavingChallenge(
      @RequestBody SavingChallengeDto dto) {
    SavingChallenge challenge = savingChallengeService.createChallenge(dto.toEntity());
    return new ResponseEntity<>(challenge, HttpStatus.CREATED);
  }

  /**
   * Update an existing saving challenge.
   *
   * @param id The id of the saving challenge to update
   * @param dto The DTO representing the updated saving challenge
   * @return The updated saving challenge
   */
  @PutMapping("/saving/{id}")
  @Operation(summary = "Update an existing saving challenge")
  public ResponseEntity<SavingChallenge> updateSavingChallenge(
      @PathVariable Long id, @RequestBody SavingChallengeDto dto) {
    SavingChallenge challenge = savingChallengeService.updateSavingChallenge(id, dto);
    return ResponseEntity.ok(challenge);
  }

  /**
   * Delete an existing saving challenge.
   *
   * @param id The id of the saving challenge to delete
   * @return A response entity with no content
   */
  @DeleteMapping("/saving/{id}")
  @Operation(summary = "Delete an existing saving challenge")
  public ResponseEntity<Void> deleteSavingChallenge(@PathVariable Long id) {
    savingChallengeService.deleteSavingChallenge(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get all saving challenges.
   *
   * @return A list of all saving challenges
   */
  @GetMapping("/saving")
  @Operation(summary = "Get all saving challenges")
  public ResponseEntity<List<SavingChallenge>> getAllSavingChallenges() {
    List<SavingChallenge> challenges = savingChallengeService.getAllSavingChallenges();
    return ResponseEntity.ok(challenges);
  }

  /**
   * Get a saving challenge by its id.
   *
   * @param id The id of the saving challenge
   * @return The saving challenge if it exists, or a 404 Not Found response otherwise
   */
  @GetMapping("/saving/{id}")
  @Operation(summary = "Get a saving challenge by id")
  public ResponseEntity<SavingChallenge> getSavingChallengeById(@PathVariable Long id) {
    Optional<SavingChallenge> challenge = savingChallengeService.getSavingChallengeById(id);
    return challenge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Add an amount to the saved amount of a saving challenge.
   *
   * @param id The id of the saving challenge
   * @param amount The amount to add
   * @return A response entity with a message
   */
  @PutMapping("/saving/{id}/add")
  @Operation(summary = "Add an amount to the saved amount")
  public ResponseEntity<String> addAmountToSavingChallenge(
      @PathVariable Long id, @RequestParam Double amount) {
    savingChallengeService.addToSavedAmount(id, amount);
    return ResponseEntity.ok("Amount added successfully");
  }
}
