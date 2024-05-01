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

  @Autowired
  public BadgeAwarder(UserBadgeService userBadgeService, BadgeRepository badgeRepository) {
    this.userBadgeService = userBadgeService;
    this.badgeRepository = badgeRepository;
  }

  /** Checks all conditions for awarding a badge to a user. */
  @Transactional
  public Badge checkAndAwardBadges(Long userId) {
    return checkAndAwardLoginBadge(userId);
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
                () -> new NotFoundException("Badge " + "with the name 'På god vei!' not found."));

    if (!userBadgeService.hasUserBadge(userId, loginBadge.getId())) {
      userBadgeService.giveUserBadge(userId, loginBadge.getId());
      return loginBadge;
    }
    return null;
  }
}
