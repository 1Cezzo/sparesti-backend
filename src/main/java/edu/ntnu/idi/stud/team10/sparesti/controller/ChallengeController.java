package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.ChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.ConsumptionChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.PurchaseChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.SavingChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/challenges")
@Tag(name = "Challenges", description = "Operations related to challenges")
public class ChallengeController {

  private final ChallengeService challengeService;
  private final PurchaseChallengeService purchaseChallengeService;
  private final SavingChallengeService savingChallengeService;
  private final ConsumptionChallengeService consumptionChallengeService;

  @Autowired
  public ChallengeController(
      ChallengeService challengeService,
      PurchaseChallengeService purchaseChallengeService,
      SavingChallengeService savingChallengeService,
      ConsumptionChallengeService consumptionChallengeService) {
    this.challengeService = challengeService;
    this.purchaseChallengeService = purchaseChallengeService;
    this.savingChallengeService = savingChallengeService;
    this.consumptionChallengeService = consumptionChallengeService;
  }

  @PostMapping("/purchase")
  @Operation(summary = "Create a new purchase challenge")
  public ResponseEntity<PurchaseChallenge> createPurchaseChallenge(
      @RequestBody PurchaseChallengeDTO dto) {
    PurchaseChallenge challenge = purchaseChallengeService.create(dto);
    return new ResponseEntity<>(challenge, HttpStatus.CREATED);
  }

  @PutMapping("/purchase/{id}")
  @Operation(summary = "Update an existing purchase challenge")
  public ResponseEntity<PurchaseChallenge> updatePurchaseChallenge(
      @PathVariable Long id, @RequestBody PurchaseChallengeDTO dto) {
    PurchaseChallenge challenge = purchaseChallengeService.update(id, dto);
    return ResponseEntity.ok(challenge);
  }

  @DeleteMapping("/purchase/{id}")
  @Operation(summary = "Delete an existing purchase challenge")
  public ResponseEntity<Void> deletePurchaseChallenge(@PathVariable Long id) {
    purchaseChallengeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/purchase")
  @Operation(summary = "Get all purchase challenges")
  public ResponseEntity<List<PurchaseChallenge>> getAllPurchaseChallenges() {
    List<PurchaseChallenge> challenges = purchaseChallengeService.getAll();
    return ResponseEntity.ok(challenges);
  }

  @GetMapping("/purchase/{id}")
  @Operation(summary = "Get a purchase challenge by id")
  public ResponseEntity<PurchaseChallenge> getPurchaseChallengeById(@PathVariable Long id) {
    Optional<PurchaseChallenge> challenge = purchaseChallengeService.getById(id);
    return challenge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/purchase/{id}/add")
  @Operation(summary = "Add an amount to the saved amount")
  public ResponseEntity<String> addAmountToPurchaseChallenge(
      @PathVariable Long id, @RequestParam Double amount) {
    try {
      if (amount > 0) {
        purchaseChallengeService.addToSavedAmount(id, amount);
        return ResponseEntity.ok("Amount added successfully");
      } else {
        return ResponseEntity.badRequest().body("Amount must be greater than 0");
      }
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/saving")
  @Operation(summary = "Create a new saving challenge")
  public ResponseEntity<SavingChallenge> createSavingChallenge(
      @RequestBody SavingChallengeDTO dto) {
    SavingChallenge challenge = savingChallengeService.create(dto);
    return new ResponseEntity<>(challenge, HttpStatus.CREATED);
  }

  @PutMapping("/saving/{id}")
  @Operation(summary = "Update an existing saving challenge")
  public ResponseEntity<SavingChallenge> updateSavingChallenge(
      @PathVariable Long id, @RequestBody SavingChallengeDTO dto) {
    SavingChallenge challenge = savingChallengeService.update(id, dto);
    return ResponseEntity.ok(challenge);
  }

  @DeleteMapping("/saving/{id}")
  @Operation(summary = "Delete an existing saving challenge")
  public ResponseEntity<Void> deleteSavingChallenge(@PathVariable Long id) {
    savingChallengeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/saving")
  @Operation(summary = "Get all saving challenges")
  public ResponseEntity<List<SavingChallenge>> getAllSavingChallenges() {
    List<SavingChallenge> challenges = savingChallengeService.getAll();
    return ResponseEntity.ok(challenges);
  }

  @GetMapping("/saving/{id}")
  @Operation(summary = "Get a saving challenge by id")
  public ResponseEntity<SavingChallenge> getSavingChallengeById(@PathVariable Long id) {
    Optional<SavingChallenge> challenge = savingChallengeService.getById(id);
    return challenge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/saving/{id}/add")
  @Operation(summary = "Add an amount to the saved amount")
  public ResponseEntity<String> addAmountToSavingChallenge(
      @PathVariable Long id, @RequestParam Double amount) {
    try {
      if (amount > 0) {
        savingChallengeService.addToSavedAmount(id, amount);
        return ResponseEntity.ok("Amount added successfully");
      } else {
        return ResponseEntity.badRequest().body("Amount must be greater than 0");
      }
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/consumption")
  @Operation(summary = "Create a new consumption challenge")
  public ResponseEntity<ConsumptionChallenge> createConsumptionChallenge(
      @RequestBody ConsumptionChallengeDTO dto) {
    ConsumptionChallenge challenge = consumptionChallengeService.create(dto);
    return new ResponseEntity<>(challenge, HttpStatus.CREATED);
  }

  @PutMapping("/consumption/{id}")
  @Operation(summary = "Update an existing consumption challenge")
  public ResponseEntity<ConsumptionChallenge> updateConsumptionChallenge(
      @PathVariable Long id, @RequestBody ConsumptionChallengeDTO dto) {
    ConsumptionChallenge challenge = consumptionChallengeService.update(id, dto);
    return ResponseEntity.ok(challenge);
  }

  @DeleteMapping("/consumption/{id}")
  @Operation(summary = "Delete an existing consumption challenge")
  public ResponseEntity<Void> deleteConsumptionChallenge(@PathVariable Long id) {
    consumptionChallengeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/consumption")
  @Operation(summary = "Get all consumption challenges")
  public ResponseEntity<List<ConsumptionChallenge>> getAllConsumptionChallenges() {
    List<ConsumptionChallenge> challenges = consumptionChallengeService.getAll();
    return ResponseEntity.ok(challenges);
  }

  @GetMapping("/consumption/{id}")
  @Operation(summary = "Get a consumption challenge by id")
  public ResponseEntity<ConsumptionChallenge> getConsumptionChallengeById(@PathVariable Long id) {
    Optional<ConsumptionChallenge> challenge = consumptionChallengeService.getById(id);
    return challenge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/consumption/{id}/add")
  @Operation(summary = "Add an amount to the saved amount")
  public ResponseEntity<String> addAmountToConsumptionChallenge(
      @PathVariable Long id, @RequestParam Double amount) {
    try {
      if (amount > 0) {
        consumptionChallengeService.addToSavedAmount(id, amount);
        return ResponseEntity.ok("Amount added successfully");
      } else {
        return ResponseEntity.badRequest().body("Amount must be greater than 0");
      }
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
