package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.service.BadgeService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserBadgeService;
import edu.ntnu.idi.stud.team10.sparesti.util.TokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/** Operations related to badges of users. */
@RestController
@RequestMapping("/api/badges")
@Tag(name = "Badges", description = "Operations related to badges")
public class BadgeController {
  private final BadgeService badgeService;
  private final UserBadgeService userBadgeService;

  @Autowired
  public BadgeController(final BadgeService badgeService, final UserBadgeService userBadgeService) {
    this.badgeService = badgeService;
    this.userBadgeService = userBadgeService;
  }

  /**
   * Fetch all badges.
   *
   * @return a list of all badges.
   */
  @GetMapping
  @Operation(summary = "Get all badges")
  public ResponseEntity<List<Badge>> getAllBadges() {
    return ResponseEntity.ok(badgeService.getAllBadges());
  }

  /**
   * Creates a new badge.
   *
   * @param badgeDto the badge being created.
   * @return The created badge
   */
  @PostMapping("/create")
  @Operation(summary = "Create a new badge")
  public ResponseEntity<BadgeDto> createBadge(@RequestBody final BadgeDto badgeDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(badgeService.createBadge(badgeDto));
  }

  /**
   * Gets a badge's rarity.
   *
   * @param badgeId the ID of the badge.
   * @return The percentage of all users who have earned the badge
   */
  @GetMapping("/rarity/{badgeId}")
  @Operation(summary = "Get a badge's rarity")
  public ResponseEntity<Double> getBadgeRarity(@PathVariable final Long badgeId) {
    return ResponseEntity.ok(badgeService.findBadgeRarity(badgeId));
  }

  /**
   * Return's the details of a badge, so it can be shown on the front-end.
   *
   * @param badgeId the ID of the badge
   * @return a DTO of the main details of the badge.
   */
  @GetMapping("/{badgeId}")
  @Operation(summary = "Get a singular badge's info")
  public ResponseEntity<BadgeDto> getBadgeInfo(@PathVariable final Long badgeId) {
    return ResponseEntity.ok(badgeService.getBadgeById(badgeId));
  }

  /**
   * Get all badges earned by a user.
   *
   * @param token the JWT token.
   * @return a set of all badges earned by the user.
   */
  @GetMapping("/user")
  @Operation(summary = "Get all badges for a user")
  public ResponseEntity<Set<Map<String, Object>>> getAllBadgesByUserId(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    Set<Map<String, Object>> badges = userBadgeService.getAllBadgesByUserId(userId);
    return ResponseEntity.ok(badges);
  }

  /**
   * Awards a badge to a user.
   *
   * @param userId the user's ID.
   * @param badgeId the badge's ID.
   * @return 200 OK if successful.
   */
  @PostMapping("/{userId}/give/{badgeId}")
  @Operation(summary = "Give a user a badge")
  public ResponseEntity<Void> giveUserBadge(@PathVariable Long userId, @PathVariable Long badgeId) {
    userBadgeService.giveUserBadge(userId, badgeId);
    return ResponseEntity.ok().build();
  }

  /**
   * Removes a badge from a user.
   *
   * @param userId the user's ID.
   * @param badgeId the badge's ID.
   * @return 204 OK if successful.
   */
  @DeleteMapping("/{userId}/remove/{badgeId}")
  @Operation(summary = "Remove a badge from a user")
  public ResponseEntity<Void> removeUserBadge(
      @PathVariable Long userId, @PathVariable Long badgeId) {
    userBadgeService.removeUserBadge(userId, badgeId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Deletes a badge by its ID.
   *
   * @param badgeId the badge's ID.
   * @return A list of users who have the badge.
   */
  @GetMapping("/badge/{badgeId}/users")
  @Operation(summary = "Get all users with the given badge")
  public ResponseEntity<List<Map<String, Object>>> getUsersByBadge(@PathVariable Long badgeId) {
    List<Map<String, Object>> users = userBadgeService.getUsersByBadge(badgeId);
    return ResponseEntity.ok(users);
  }
}
