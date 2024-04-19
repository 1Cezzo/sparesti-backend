package edu.ntnu.idi.stud.team10.sparesti.controller;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.service.BadgeService;
import edu.ntnu.idi.stud.team10.sparesti.util.InvalidIdException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Operations related to badges of users.
 */
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
     * Creates a new badge.
     *
     * @param badgeDto the badge being created.
     * @return The created badge
     */
    @PostMapping("/create")
    @Operation(summary = "Create a new badge")
    public BadgeDto createBadge(@RequestBody final BadgeDto badgeDto) {
        return badgeService.createBadge(badgeDto);
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
        try {
            return ResponseEntity.ok(badgeService.findBadgeRarity(badgeId));
        }
        catch (InvalidIdException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{badgeId}")
    @Operation(summary = "Get a singular badge's info")
    public ResponseEntity<BadgeDto> getBadgeInfo(@PathVariable final Long badgeId) {
        try {
            return ResponseEntity.ok(badgeService.getBadgeById(badgeId).get());
        } catch (InvalidIdException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
