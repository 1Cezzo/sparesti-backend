package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.service.*;
import edu.ntnu.idi.stud.team10.sparesti.util.TokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/challenges")
@Tag(name = "Challenges", description = "Operations related to challenges")
/** Controller class for the Challenge entity. */
public class ChallengeController {

  private final UserChallengeService userChallengeService;
  private final UserInfoService userInfoService;

  @Autowired
  public ChallengeController(
      UserChallengeService userChallengeService, UserInfoService userInfoService) {
    this.userChallengeService = userChallengeService;
    this.userInfoService = userInfoService;
  }

  /**
   * Add a challenge to a user.
   *
   * @param token the JWT token.
   * @param challengeId the id of the challenge.
   * @return the updated user DTO.
   */
  @PostMapping("/users/challenges/{challengeId}")
  @Operation(summary = "Add a challenge to a user")
  public ResponseEntity<UserDto> addChallengeToUser(
      @AuthenticationPrincipal Jwt token, @PathVariable Long challengeId) {
    Long userId = TokenParser.extractUserId(token);
    UserDto userDto = userChallengeService.addChallengeToUser(userId, challengeId);
    return ResponseEntity.ok(userDto);
  }

  /**
   * Remove a challenge from a user.
   *
   * @param token the JWT token.
   * @param challengeId the id of the challenge.
   * @return the updated user DTO.
   */
  @DeleteMapping("/users/challenges/{challengeId}")
  @Operation(summary = "Remove a challenge from a user")
  public ResponseEntity<UserDto> removeChallengeFromUser(
      @AuthenticationPrincipal Jwt token, @PathVariable Long challengeId) {
    Long userId = TokenParser.extractUserId(token);
    UserDto userDto = userChallengeService.removeChallengeFromUser(userId, challengeId);
    return ResponseEntity.ok(userDto);
  }

  /**
   * Fetch all consumption challenges for a user.
   *
   * @param token the JWT token.
   * @return a list of consumption challenges.
   */
  @GetMapping("/users/consumption-challenges")
  @Operation(summary = "Fetch all consumption challenges for a user")
  public ResponseEntity<List<ConsumptionChallengeDto>> fetchConsumptionChallengesForUser(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    List<ConsumptionChallengeDto> consumptionChallenges =
        userChallengeService.fetchConsumptionChallengesForUser(userId);
    return ResponseEntity.ok(consumptionChallenges);
  }

  /**
   * Fetch all purchase challenges for a user.
   *
   * @param token the JWT token.
   * @return a list of purchase challenges.
   */
  @GetMapping("/users/purchase-challenges")
  @Operation(summary = "Fetch all purchase challenges for a user")
  public ResponseEntity<List<PurchaseChallengeDto>> fetchPurchaseChallengesForUser(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    List<PurchaseChallengeDto> purchaseChallenges =
        userChallengeService.fetchPurchaseChallengesForUser(userId);
    return ResponseEntity.ok(purchaseChallenges);
  }

  /**
   * Fetch all saving challenges for a user.
   *
   * @param token the JWT token.
   * @return a list of saving challenges.
   */
  @GetMapping("/users/saving-challenges")
  @Operation(summary = "Fetch all saving challenges for a user")
  public ResponseEntity<List<SavingChallengeDto>> fetchSavingChallengesForUser(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    List<SavingChallengeDto> savingChallenges =
        userChallengeService.fetchSavingChallengesForUser(userId);
    return ResponseEntity.ok(savingChallenges);
  }

  /**
   * Get sorted challenges for a user.
   *
   * @param token the JWT token.
   * @return a list of the challenges.
   */
  @GetMapping("/users/challenges")
  @Operation(summary = "Get all challenges for a user")
  public ResponseEntity<List<ChallengeDto>> getChallengesByUser(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    List challenges = userChallengeService.getSortedChallengesByUser(userId);
    return ResponseEntity.ok(challenges);
  }

  /**
   * Get active challenges for a user.
   *
   * @param token the JWT token.
   * @return a list of the active challenges.
   */
  @GetMapping("/users/active-challenges")
  @Operation(summary = "Get active challenges for a user")
  public ResponseEntity<List<ChallengeDto>> getActiveChallengesByUser(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    List challenges = userChallengeService.getActiveSortedChallengesByUser(userId);
    return ResponseEntity.ok(challenges);
  }

  /**
   * Suggest an AI generated challenge to a user.
   *
   * @param userEmail the email of the user.
   * @return a chat response with a suggested challenge.
   */
  @GetMapping("/suggest-ai-challenge")
  @Operation(summary = "Suggest an AI generated challenge to a user")
  public ChallengeDto suggestChallenge(String userEmail) {
    UserInfoDto userInfo = userInfoService.getUserInfoByEmail(userEmail);
    return userChallengeService.generateChatResponse(userInfo);
  }

  /**
   * Suggest a random challenge to a user.
   *
   * @param userEmail the email of the user.
   * @return a chat response with a suggested challenge.
   */
  @GetMapping("/suggest-random-challenge")
  @Operation(summary = "Suggest a random challenge to a user")
  public ChallengeDto suggestRandomChallenge(String userEmail) {
    UserInfoDto userInfo = userInfoService.getUserInfoByEmail(userEmail);
    return userChallengeService.generateRandomChallenge(userInfo);
  }

  /**
   * Update completed value of a challenge.
   *
   * @param token the JWT token.
   * @param challengeId the id of the challenge.
   */
  @PutMapping("/users/challenges/{challengeId}")
  @Operation(summary = "Update completed value of a challenge")
  public ResponseEntity<Boolean> updateCompletedValueOfChallenge(
      @AuthenticationPrincipal Jwt token, @PathVariable Long challengeId) {
    Long userId = TokenParser.extractUserId(token);
    boolean completed = userChallengeService.updateCompleted(userId, challengeId);
    return ResponseEntity.ok(completed);
  }
}
