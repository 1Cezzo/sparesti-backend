package edu.ntnu.idi.stud.team10.sparesti.controller;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/badges")
@Tag(name = "Badges", description = "Operations related to badges")
public class BadgeController {
    private final BadgeService badgeService;

    @Autowired
    public BadgeController(final BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new badge")
    public BadgeDto createBadge(@RequestBody final BadgeDto badgeDto) {
        return badgeService.createBadge(badgeDto);
    }
}
