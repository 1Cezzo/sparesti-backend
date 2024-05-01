package edu.ntnu.idi.stud.team10.sparesti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for awarding badges to users. */
@Service
public class BadgeAwarder {

  private final UserBadgeService userBadgeService;
  private final BadgeRepository badgeRepository;
  private final UserChallengeService userChallengeService;

  @Autowired
  public BadgeAwarder(
      UserBadgeService userBadgeService,
      BadgeRepository badgeRepository,
      UserChallengeService userChallengeService) {
    this.userBadgeService = userBadgeService;
    this.badgeRepository = badgeRepository;
    this.userChallengeService = userChallengeService;
  }

  /**
   * Checks if the user has completed enough challenges to earn a badge.
   *
   * @param userId (Long): The User's unique ID.
   * @return (Badge): The badge that the user has earned, or null if no badge has been earned.
   */
  @Transactional
  public Badge checkAndAwardBadges(Long userId) {
    Badge loginBadge = checkAndAwardLoginBadge(userId);
    if (loginBadge != null) {
      return loginBadge;
    }

    Badge threeChallengesBadge = checkAndAwardBadge(userId, "3 Utfordringer!", 3);
    if (threeChallengesBadge != null) {
      return threeChallengesBadge;
    }

    Badge fiveChallengesBadge = checkAndAwardBadge(userId, "10 Utfordringer!", 10);
    if (fiveChallengesBadge != null) {
      return fiveChallengesBadge;
    }

    Badge fifteenChallengesBadge = checkAndAwardBadge(userId, "15 Utfordringer!", 15);
    if (fifteenChallengesBadge != null) {
      return fifteenChallengesBadge;
    }

    return null;
  }

  /**
   * Awards login badge to user.
   *
   * @param userId (Long): The User's unique ID.
   */
  @Transactional
  public Badge checkAndAwardLoginBadge(Long userId) {
    Badge loginBadge =
        badgeRepository
            .findByName("På god vei!")
            .orElseThrow(
                () -> new NotFoundException("Badge with the name 'På god vei!' not found."));

    if (userBadgeService.hasUserBadge(userId, loginBadge.getId())) {
      return null;
    }
    return loginBadge;
  }

  /**
   * Awards challenges badge to user if they have completed the specified amount of challenges.
   *
   * @param userId (Long): The User's unique ID. badgeName (String): The name of the badge.
   *     requiredChallenges (int): The number of challenges required to earn the badge.
   */
  @Transactional
  public Badge checkAndAwardBadge(Long userId, String badgeName, int requiredChallenges) {
    Badge badge = badgeRepository.findByName(badgeName).orElse(null);

    if (badge == null || userBadgeService.hasUserBadge(userId, badge.getId())) {
      return null;
    }

    if (userChallengeService.hasNumberOfCompletedChallenges(userId, requiredChallenges)) {
      return badge;
    }

    return null;
  }
}
