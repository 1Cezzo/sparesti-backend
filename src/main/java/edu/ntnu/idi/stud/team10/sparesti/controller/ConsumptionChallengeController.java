package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.ConsumptionChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/consumption-challenges")
@Tag(
    name = "Consumption Challenges",
    description = "Endpoints for handling consumption challenges.")
/** Controller for handling consumption challenges. */
public class ConsumptionChallengeController {

  private final ConsumptionChallengeService consumptionChallengeService;
  private final ChallengeMapper challengeMapper;

  /**
   * Constructor for ConsumptionChallengeController.
   *
   * @param consumptionChallengeService The consumption challenge service
   * @param challengeMapper The challenge mapper
   */
  @Autowired
  public ConsumptionChallengeController(
      ConsumptionChallengeService consumptionChallengeService, ChallengeMapper challengeMapper) {
    this.consumptionChallengeService = consumptionChallengeService;
    this.challengeMapper = challengeMapper;
  }

  /**
   * Create a new consumption challenge.
   *
   * @param dto The DTO representing the consumption challenge to create
   * @return The created consumption challenge
   */
  @PostMapping("/consumption")
  @Operation(summary = "Create a new consumption challenge")
  public ResponseEntity<ConsumptionChallenge> createConsumptionChallenge(
      @RequestBody ConsumptionChallengeDto dto) {
    ConsumptionChallenge challenge =
        consumptionChallengeService.createChallenge(
            (ConsumptionChallenge) challengeMapper.toEntity(dto));
    return new ResponseEntity<>(challenge, HttpStatus.CREATED);
  }

  /**
   * Update an existing consumption challenge.
   *
   * @param id The id of the consumption challenge to update
   * @param dto The DTO representing the updated consumption challenge
   * @return The updated consumption challenge
   */
  @PutMapping("/consumption/{id}")
  @Operation(summary = "Update an existing consumption challenge")
  public ResponseEntity<ConsumptionChallenge> updateConsumptionChallenge(
      @PathVariable Long id, @RequestBody ConsumptionChallengeDto dto) {
    ConsumptionChallenge challenge =
        consumptionChallengeService.updateConsumptionChallenge(id, dto);
    return ResponseEntity.ok(challenge);
  }

  /**
   * Delete an existing consumption challenge.
   *
   * @param id The id of the consumption challenge to delete
   * @return A response entity with no content
   */
  @DeleteMapping("/consumption/{id}")
  @Operation(summary = "Delete an existing consumption challenge")
  public ResponseEntity<Void> deleteConsumptionChallenge(@PathVariable Long id) {
    consumptionChallengeService.deleteConsumptionChallenge(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get all consumption challenges.
   *
   * @return A list of all consumption challenges
   */
  @GetMapping("/consumption")
  @Operation(summary = "Get all consumption challenges")
  public ResponseEntity<List<ConsumptionChallenge>> getAllConsumptionChallenges() {
    List<ConsumptionChallenge> challenges =
        consumptionChallengeService.getAllConsumptionChallenges();
    return ResponseEntity.ok(challenges);
  }

  /**
   * Get a consumption challenge by its id.
   *
   * @param id The id of the consumption challenge
   * @return The consumption challenge if it exists, or a 404 Not Found response otherwise
   */
  @GetMapping("/consumption/{id}")
  @Operation(summary = "Get a consumption challenge by id")
  public ResponseEntity<ConsumptionChallenge> getConsumptionChallengeById(@PathVariable Long id) {
    Optional<ConsumptionChallenge> challenge =
        consumptionChallengeService.getConsumptionChallengeById(id);
    return challenge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Add an amount to the saved amount of a consumption challenge.
   *
   * @param id The id of the consumption challenge
   * @param amount The amount to add
   * @return A response entity with a message
   */
  @PutMapping("/consumption/{id}/add")
  @Operation(summary = "Add an amount to the saved amount")
  public ResponseEntity<String> addAmountToConsumptionChallenge(
      @PathVariable Long id, @RequestParam Double amount) {
    consumptionChallengeService.addToSavedAmount(id, amount);
    return ResponseEntity.ok(amount + " added successfully");
  }
}
