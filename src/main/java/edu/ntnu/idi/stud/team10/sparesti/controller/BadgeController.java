package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/** Operations related to badges of users. */
@RestController
@RequestMapping("/api/badges")
@Tag(name = "Badges", description = "Operations related to badges")
public class BadgeController {
  private final BadgeService badgeService;

  @Autowired
  public BadgeController(final BadgeService badgeService) {
    this.badgeService = badgeService;
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
}
