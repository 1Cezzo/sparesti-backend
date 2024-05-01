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
  private final SavingsGoalService savingsGoalService;
  private final UserService userService;
  private final BudgetService budgetService;

  @Autowired
  public BadgeAwarder(
      UserBadgeService userBadgeService,
      BadgeRepository badgeRepository,
      UserChallengeService userChallengeService,
      SavingsGoalService savingsGoalService,
      UserService userService,
      BudgetService budgetService) {
    this.userBadgeService = userBadgeService;
    this.badgeRepository = badgeRepository;
    this.userChallengeService = userChallengeService;
    this.savingsGoalService = savingsGoalService;
    this.userService = userService;
    this.budgetService = budgetService;
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

    Badge threeChallengesBadge = checkAndAwardChallengeBadge(userId, "3 Utfordringer!", 3);
    if (threeChallengesBadge != null) {
      return threeChallengesBadge;
    }

    Badge fiveChallengesBadge = checkAndAwardChallengeBadge(userId, "10 Utfordringer!", 10);
    if (fiveChallengesBadge != null) {
      return fiveChallengesBadge;
    }

    Badge fifteenChallengesBadge = checkAndAwardChallengeBadge(userId, "15 Utfordringer!", 15);
    if (fifteenChallengesBadge != null) {
      return fifteenChallengesBadge;
    }

    Badge savingsGoalBadge = checkAndAwardSavingsGoalBadge(userId);
    if (savingsGoalBadge != null) {
      return savingsGoalBadge;
    }

    Badge sharedSavingsGoalBadge = checkAndAwardSharedSavingsGoalBadge(userId);
    if (sharedSavingsGoalBadge != null) {
      return sharedSavingsGoalBadge;
    }

    Badge profilePictureBadge = checkAndAwardProfilePictureBadge(userId);
    if (profilePictureBadge != null) {
      return profilePictureBadge;
    }

    Badge budgetBadge = checkAndAwardBudgetBadge(userId);
    if (budgetBadge != null) {
      return budgetBadge;
    }

    return null;
  }

  /**
   * Awards login badge to user.
   *
   * @param userId (Long): The User's unique ID.
   * @throws NotFoundException If the badge with the name 'På god vei!' is not found.
   * @return (Badge): The badge that the user has earned, or null if no badge has been earned.
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
   * @throws NotFoundException If the badge with the given name is not found.
   * @return (Badge): The badge that the user has earned, or null if no badge has been earned.
   */
  @Transactional
  public Badge checkAndAwardChallengeBadge(Long userId, String badgeName, int requiredChallenges) {
    Badge badge = badgeRepository.findByName(badgeName).orElse(null);

    if (badge == null || userBadgeService.hasUserBadge(userId, badge.getId())) {
      return null;
    }

    if (userChallengeService.hasNumberOfCompletedChallenges(userId, requiredChallenges)) {
      return badge;
    }

    return null;
  }

  /**
   * Awards savings goal badge to user if they have completed a savings goal.
   *
   * @param userId (Long): The User's unique ID.
   * @throws NotFoundException If the badge with the name 'Sparemål oppnådd' is not found.
   * @return (Badge): The badge that the user has earned, or null if no badge has been earned.
   */
  @Transactional
  public Badge checkAndAwardSavingsGoalBadge(Long userId) {
    Badge badge =
        badgeRepository
            .findByName("Sparemål oppnådd")
            .orElseThrow(
                () -> new NotFoundException("Badge with the name 'Sparemål oppnådd' not found."));

    if (userBadgeService.hasUserBadge(userId, badge.getId())) {
      return null;
    }

    if (savingsGoalService.hasCompletedSavingsGoal(userId)) {
      return badge;
    }

    return null;
  }

  /**
   * Awards shared savings goal badge to user if they have shared a savings goal.
   *
   * @param userId (Long): The User's unique ID.
   * @throws NotFoundException If the badge with the name 'Delt sparemål' is not found.
   * @return (Badge): The badge that the user has earned, or null if no badge has been earned.
   */
  @Transactional
  public Badge checkAndAwardSharedSavingsGoalBadge(Long userId) {
    Badge badge =
        badgeRepository
            .findByName("Delt sparemål")
            .orElseThrow(
                () -> new NotFoundException("Badge with the name 'Delt sparemål' not found."));

    if (userBadgeService.hasUserBadge(userId, badge.getId())) {
      return null;
    }

    if (savingsGoalService.hasSharedSavingsGoal(userId)) {
      return badge;
    }

    return null;
  }

  /**
   * Awards profile picture badge if user has uploaded a profile picture.
   *
   * @param userId (Long): The User's unique ID.
   * @throws NotFoundException If the badge with the name 'Profilbilde' is not found.
   * @return (Badge): The badge that the user has earned, or null if no badge has been earned.
   */
  @Transactional
  public Badge checkAndAwardProfilePictureBadge(Long userId) {
    Badge badge =
        badgeRepository
            .findByName("Profilbilde")
            .orElseThrow(
                () -> new NotFoundException("Badge with the name 'Profilbilde' not found."));

    if (userBadgeService.hasUserBadge(userId, badge.getId())) {
      return null;
    }

    if (userService.hasProfilePicture(userId)) {
      return badge;
    }

    return null;
  }

  /**
   * Awards budget badge to user if they have created a budget.
   *
   * @param userId (Long): The User's unique ID.
   * @throws NotFoundException If the badge with the name 'Budsjett' is not found.
   * @return (Badge): The badge that the user has earned, or null if no badge has been earned.
   */
  @Transactional
  public Badge checkAndAwardBudgetBadge(Long userId) {
    Badge badge =
        badgeRepository
            .findByName("Budsjett")
            .orElseThrow(() -> new NotFoundException("Badge with the name 'Budsjett' not found."));

    if (userBadgeService.hasUserBadge(userId, badge.getId())) {
      return null;
    }

    if (budgetService.hasCreatedBudget(userId)) {
      return badge;
    }

    return null;
  }
}
