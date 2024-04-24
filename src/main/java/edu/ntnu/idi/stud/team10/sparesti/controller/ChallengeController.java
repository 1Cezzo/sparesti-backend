package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.ConsumptionChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.PurchaseChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.SavingChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/challenges")
@Tag(name = "Challenges", description = "Operations related to challenges")
/** Controller class for the Challenge entity. */
public class ChallengeController {

  private final PurchaseChallengeService purchaseChallengeService;
  private final SavingChallengeService savingChallengeService;
  private final ConsumptionChallengeService consumptionChallengeService;
  private final UserChallengeService userChallengeService;

  @Autowired
  public ChallengeController(
      UserChallengeService userChallengeService,
      @Qualifier("purchaseChallengeService") PurchaseChallengeService purchaseChallengeService,
      @Qualifier("savingChallengeService") SavingChallengeService savingChallengeService,
      @Qualifier("consumptionChallengeService") ConsumptionChallengeService consumptionChallengeService) {
    this.purchaseChallengeService = purchaseChallengeService;
    this.savingChallengeService = savingChallengeService;
    this.consumptionChallengeService = consumptionChallengeService;
    this.userChallengeService = userChallengeService;
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
      @RequestBody PurchaseChallengeDTO dto) {
    PurchaseChallenge challenge = purchaseChallengeService.createChallenge(dto.toEntity());
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
      @PathVariable Long id, @RequestBody PurchaseChallengeDTO dto) {
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

  /**
   * Create a new saving challenge.
   *
   * @param dto The DTO representing the saving challenge to create
   * @return The created saving challenge
   */
  @PostMapping("/saving")
  @Operation(summary = "Create a new saving challenge")
  public ResponseEntity<SavingChallenge> createSavingChallenge(
      @RequestBody SavingChallengeDTO dto) {
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
      @PathVariable Long id, @RequestBody SavingChallengeDTO dto) {
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

  /**
   * Create a new consumption challenge.
   *
   * @param dto The DTO representing the consumption challenge to create
   * @return The created consumption challenge
   */
  @PostMapping("/consumption")
  @Operation(summary = "Create a new consumption challenge")
  public ResponseEntity<ConsumptionChallenge> createConsumptionChallenge(
      @RequestBody ConsumptionChallengeDTO dto) {
    ConsumptionChallenge challenge = consumptionChallengeService.createChallenge(dto.toEntity());
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
      @PathVariable Long id, @RequestBody ConsumptionChallengeDTO dto) {
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

  /**
   * Add a challenge to a user.
   *
   * @param userId the id of the user.
   * @param challengeId the id of the challenge.
   * @return the updated user DTO.
   */
  @PostMapping("/users/{userId}/challenges/{challengeId}")
  @Operation(summary = "Add a challenge to a user")
  public ResponseEntity<UserDto> addChallengeToUser(
      @PathVariable Long userId, @PathVariable Long challengeId) {
    UserDto userDto = userChallengeService.addChallengeToUser(userId, challengeId);
    return ResponseEntity.ok(userDto);
  }

  /**
   * Remove a challenge from a user.
   *
   * @param userId the id of the user.
   * @param challengeId the id of the challenge.
   * @return the updated user DTO.
   */
  @DeleteMapping("/users/{userId}/challenges/{challengeId}")
  @Operation(summary = "Remove a challenge from a user")
  public ResponseEntity<UserDto> removeChallengeFromUser(
      @PathVariable Long userId, @PathVariable Long challengeId) {
    UserDto userDto = userChallengeService.removeChallengeFromUser(userId, challengeId);
    return ResponseEntity.ok(userDto);
  }

  /**
   * Fetch all consumption challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of consumption challenges.
   */
  @GetMapping("/users/{userId}/consumption-challenges")
  @Operation(summary = "Fetch all consumption challenges for a user")
  public ResponseEntity<List<ConsumptionChallengeDTO>> fetchConsumptionChallengesForUser(
      @PathVariable Long userId) {
    List<ConsumptionChallengeDTO> consumptionChallenges =
        userChallengeService.fetchConsumptionChallengesForUser(userId);
    return ResponseEntity.ok(consumptionChallenges);
  }

  /**
   * Fetch all purchase challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of purchase challenges.
   */
  @GetMapping("/users/{userId}/purchase-challenges")
  @Operation(summary = "Fetch all purchase challenges for a user")
  public ResponseEntity<List<PurchaseChallengeDTO>> fetchPurchaseChallengesForUser(
      @PathVariable Long userId) {
    List<PurchaseChallengeDTO> purchaseChallenges =
        userChallengeService.fetchPurchaseChallengesForUser(userId);
    return ResponseEntity.ok(purchaseChallenges);
  }

  /**
   * Fetch all saving challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of saving challenges.
   */
  @GetMapping("/users/{userId}/saving-challenges")
  @Operation(summary = "Fetch all saving challenges for a user")
  public ResponseEntity<List<SavingChallengeDTO>> fetchSavingChallengesForUser(
      @PathVariable Long userId) {
    List<SavingChallengeDTO> savingChallenges =
        userChallengeService.fetchSavingChallengesForUser(userId);
    return ResponseEntity.ok(savingChallenges);
  }

  /**
   * Get sorted challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of the challenges.
   */
  @GetMapping("/users/{userId}/challenges")
  @Operation(summary = "Get all challenges for a user")
  public ResponseEntity getChallengesByUser(@PathVariable Long userId) {
    List challenges = userChallengeService.getSortedChallengesByUser(userId);

    return ResponseEntity.ok(challenges);
  }
}
