package edu.ntnu.idi.stud.team10.sparesti.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.service.BadgeService;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

  private final BadgeService badgeService;

  public DataLoader(BadgeService badgeService) {
    this.badgeService = badgeService;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    initialize();
  }

  private void resetBadges() {
    badgeService.deleteAllBadges(); // Implement this method in BadgeService to delete all badges
  }

  private void initialize() {
    if (!badgeService.getAllBadges().isEmpty()) {
      resetBadges();
    }
    createBadge(
        "Pig Badge",
        "Badge for being a pig lover",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_pig_cropped.png");
    createBadge(
        "Red Bull Badge",
        "Badge for energy drink enthusiasts",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_cropped_red_bull.png");
    createBadge(
        "Coffee Badge",
        "Badge for coffee addicts",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_cropped_coffee.png");
  }

  private void createBadge(String name, String description, String imageUrl) {
    BadgeDto badgeDto = new BadgeDto();
    badgeDto.setName(name);
    badgeDto.setDescription(description);
    badgeDto.setImageUrl(imageUrl);
    badgeService.createBadge(badgeDto);
  }
}
