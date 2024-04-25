package edu.ntnu.idi.stud.team10.sparesti.util;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.*;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

  private final BadgeService badgeService;
  private final UserBadgeService userBadgeService;
  private final UserService userService;
  private final ConsumptionChallengeService consumptionChallengeService;
  private final UserChallengeService userChallengeService;

  public DataLoader(
      BadgeService badgeService,
      UserBadgeService userBadgeService,
      UserService userService,
      ConsumptionChallengeService consumptionChallengeService,
      UserChallengeService userChallengeService) {
    this.badgeService = badgeService;
    this.userBadgeService = userBadgeService;
    this.userService = userService;
    this.consumptionChallengeService = consumptionChallengeService;
    this.userChallengeService = userChallengeService;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    initialize();
  }

  private void resetBadges() {
    List<Badge> allBadges = badgeService.getAllBadges();
    for (Badge badge : allBadges) {
      userBadgeService.deleteBadgeWithAssociatedUserBadges(badge.getId());
    }
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

    try {
      UserDto adminUser = userService.getUserByEmail("admin@admin");
      Hibernate.initialize(adminUser.getSavingsGoals());
      Hibernate.initialize(adminUser.getChallenges());
    } catch (NotFoundException e) {
      // User not found, proceed with creating the admin user
      UserDto adminUser = new UserDto();
      adminUser.setEmail("admin@admin");
      adminUser.setPassword("password");
      adminUser.setProfilePictureUrl(
          "https://quiz-project-fullstack.s3.eu-north-1.amazonaws.com/09663791-e23b-427b-b8d4-a341664f4f0a_amongus.png");
      userService.addUser(adminUser);
    }

    Long adminUserId = userService.getUserByEmail("admin@admin").getId();

    Long badgeId = badgeService.getAllBadges().get(0).getId();

    userBadgeService.giveUserBadge(adminUserId, badgeId);

    if (!userChallengeService.getSortedChallengesByUser(adminUserId).isEmpty()) {
      return;
    }

    ConsumptionChallenge challenge1 = new ConsumptionChallenge();
    challenge1.setTimeInterval(TimeInterval.valueOf("DAILY"));
    challenge1.setDifficultyLevel(DifficultyLevel.valueOf("EASY"));
    challenge1.setTitle("Spar 100 kr på kaffe");
    challenge1.setDescription("Ikke kjøp kaffe i dag og spar 10 kr");
    challenge1.setMediaUrl("☕");
    challenge1.setTargetAmount(100);
    challenge1.setProductCategory("kaffe");
    challenge1.setReductionPercentage(15);

    // Set challenge properties
    consumptionChallengeService.createChallenge(challenge1);
    userChallengeService.addChallengeToUser(adminUserId, challenge1.getId());

    ConsumptionChallenge challenge2 = new ConsumptionChallenge();
    challenge2.setTimeInterval(TimeInterval.valueOf("WEEKLY"));
    challenge2.setDifficultyLevel(DifficultyLevel.valueOf("MEDIUM"));
    challenge2.setTitle("Spar 100 kr på Red Bull");
    challenge2.setDescription("Ikke kjøp Red Bull i dag og spar 10 kr");
    challenge2.setMediaUrl("🧃");
    challenge2.setTargetAmount(100);
    challenge2.setProductCategory("Red Bull");
    challenge2.setReductionPercentage(15);

    consumptionChallengeService.createChallenge(challenge2);
    userChallengeService.addChallengeToUser(adminUserId, challenge2.getId());
  }

  private void createBadge(String name, String description, String imageUrl) {
    BadgeDto badgeDto = new BadgeDto();
    badgeDto.setName(name);
    badgeDto.setDescription(description);
    badgeDto.setImageUrl(imageUrl);
    badgeService.createBadge(badgeDto);
  }
}
