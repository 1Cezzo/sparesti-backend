package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.PurchaseChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/purchase-challenges")
@Tag(name = "Purchase Challenges", description = "Endpoints for handling purchase challenges.")
/** Controller for handling purchase challenges. */
public class PurchaseChallengeController {

  private final PurchaseChallengeService purchaseChallengeService;
  private final ChallengeMapper challengeMapper;

  /**
   * Constructor for PurchaseChallengeController.
   *
   * @param purchaseChallengeService The purchase challenge service
   * @param challengeMapper The challenge mapper
   */
  @Autowired
  public PurchaseChallengeController(
      PurchaseChallengeService purchaseChallengeService, ChallengeMapper challengeMapper) {
    this.purchaseChallengeService = purchaseChallengeService;
    this.challengeMapper = challengeMapper;
  }

  /**
   * Create a new purchase challenge.
   *
   * @param dto The DTO representing the purchase challenge to create
   * @return The created purchase challenge
   */
  @PostMapping("/purchase")
  @Operation(summary = "Create a new purchase challenge")
  public ResponseEntity<PurchaseChallenge> createPurchaseChallenge(
      @RequestBody PurchaseChallengeDto dto) {
    PurchaseChallenge challenge =
        purchaseChallengeService.createChallenge((PurchaseChallenge) challengeMapper.toEntity(dto));
    return new ResponseEntity<>(challenge, HttpStatus.CREATED);
  }

  /**
   * Update an existing purchase challenge.
   *
   * @param id The id of the purchase challenge to update
   * @param dto The DTO representing the updated purchase challenge
   * @return The updated purchase challenge
   */
  @PutMapping("/purchase/{id}")
  @Operation(summary = "Update an existing purchase challenge")
  public ResponseEntity<PurchaseChallenge> updatePurchaseChallenge(
      @PathVariable Long id, @RequestBody PurchaseChallengeDto dto) {
    PurchaseChallenge challenge = purchaseChallengeService.updatePurchaseChallenge(id, dto);
    return ResponseEntity.ok(challenge);
  }

  /**
   * Delete an existing purchase challenge.
   *
   * @param id The id of the purchase challenge to delete
   * @return A response entity with no content
   */
  @DeleteMapping("/purchase/{id}")
  @Operation(summary = "Delete an existing purchase challenge")
  public ResponseEntity<Void> deletePurchaseChallenge(@PathVariable Long id) {
    purchaseChallengeService.deletePurchaseChallenge(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get all purchase challenges.
   *
   * @return A list of all purchase challenges
   */
  @GetMapping("/purchase")
  @Operation(summary = "Get all purchase challenges")
  public ResponseEntity<List<PurchaseChallenge>> getAllPurchaseChallenges() {
    List<PurchaseChallenge> challenges = purchaseChallengeService.getAllPurchaseChallenges();
    return ResponseEntity.ok(challenges);
  }

  /**
   * Get a purchase challenge by its id.
   *
   * @param id The id of the purchase challenge
   * @return The purchase challenge if it exists, or a 404 Not Found response otherwise
   */
  @GetMapping("/purchase/{id}")
  @Operation(summary = "Get a purchase challenge by id")
  public ResponseEntity<PurchaseChallenge> getPurchaseChallengeById(@PathVariable Long id) {
    Optional<PurchaseChallenge> challenge = purchaseChallengeService.getPurchaseChallengeById(id);
    return challenge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Add an amount to the saved amount of a purchase challenge.
   *
   * @param id The id of the purchase challenge
   * @param amount The amount to add
   * @return A response entity with a message
   */
  @PutMapping("/purchase/{id}/add")
  @Operation(summary = "Add an amount to the saved amount")
  public ResponseEntity<String> addAmountToPurchaseChallenge(
      @PathVariable Long id, @RequestParam Double amount) {
    purchaseChallengeService.addToSavedAmount(id, amount);
    return ResponseEntity.ok("Amount added successfully");
  }
}
