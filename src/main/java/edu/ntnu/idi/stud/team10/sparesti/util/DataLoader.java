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
        "Sparesti",
        "Medalje for spart innenfor månedtlig budjsett",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_pig_cropped.png");
    createBadge(
        "Red Bull",
        "Medalje for å spare 100 kr på Red Bull i uken",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_cropped_red_bull.png");
    createBadge(
        "Kaffe",
        "Medalje for å spare 150 kr på kaffe i uken",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/badge_cropped_coffee.png");
    createBadge(
        "På god vei!",
        "Medalje for å logge inn",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/0-challenges.png\n");
    createBadge(
        "3 Utfordringer!",
        "Medalje for å fullføre 3 utfordringer",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/3-challenges.png\n");
    createBadge(
        "10 Utfordringer!",
        "Medalje for å fullføre 10 utfordringer",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/10-challenges.png\n");
    createBadge(
        "15 Utfordringer!",
        "Medalje for å fullføre 15 utfordringer",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/15-challenges.png\n");
    createBadge(
        "Bank Id",
        "Medalje for å logge inn med Bank Id",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/bank_id.png\n");
    createBadge(
        "Kiwi",
        "Medalje for ikke å handle på Kiwi på en uke",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/kiwi.png\n");
    createBadge(
        "Rema 1001",
        "Medalje for ikke å handle på Rema 1001 på en uke",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/rema_1001.png\n");
    createBadge(
        "Spar",
        "Medalje for ikke å handle på Spar på en uke ",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/spar.png\n");
    createBadge(
        "Bunin",
        "Medalje for ikke å handle på Bunnpris på en uke",
        "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/bunin.png\n");
  }

  private void createBadge(String name, String description, String imageUrl) {
    BadgeDto badgeDto = new BadgeDto();
    badgeDto.setName(name);
    badgeDto.setDescription(description);
    badgeDto.setImageUrl(imageUrl);
    badgeService.createBadge(badgeDto);
  }
}
