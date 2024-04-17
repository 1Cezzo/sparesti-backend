package edu.ntnu.idi.stud.team10.sparesti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/challenges")
@Tag(name = "Challenges", description = "Operations related to challenges")
public class ChallengeController {

  private final ChallengeService challengeService;

  @Autowired
  public ChallengeController(ChallengeService challengeService) {
    this.challengeService = challengeService;
  }

  @PostMapping("/consumption")
  @Operation(summary = "Create a new consumption challenge")
  public ConsumptionChallengeDTO createConsumptionChallenge(
      @RequestBody ConsumptionChallengeDTO dto) {
    return challengeService.createConsumptionChallenge(dto);
  }

  @PostMapping("/purchase")
  @Operation(summary = "Create a new purchase challenge")
  public PurchaseChallengeDTO createPurchaseChallenge(@RequestBody PurchaseChallengeDTO dto) {
    return challengeService.createPurchaseChallenge(dto);
  }

  @PostMapping("/saving")
  @Operation(summary = "Create a new saving challenge")
  public SavingChallengeDTO createSavingChallenge(@RequestBody SavingChallengeDTO dto) {
    return challengeService.createSavingChallenge(dto);
  }

  // Add other endpoints as needed
}
