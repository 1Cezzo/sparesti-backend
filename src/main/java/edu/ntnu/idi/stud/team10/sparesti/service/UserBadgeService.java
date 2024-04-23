package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserBadge;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserBadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Badge entities that are connected to a user. */
@Service
public class UserBadgeService {

  private final BadgeRepository badgeRepository;
  private final UserRepository userRepository;
  private final UserBadgeRepository userBadgeRepository;

  @Autowired
  public UserBadgeService(
      BadgeRepository badgeRepository,
      UserRepository userRepository,
      UserBadgeRepository userBadgeRepository) {
    this.badgeRepository = badgeRepository;
    this.userRepository = userRepository;
    this.userBadgeRepository = userBadgeRepository;
  }

  /**
   * Returns a Set of all the badges earned by a user, as DTOs.
   *
   * @param userId (Long): The User's unique ID.
   * @return A Set of all Badges that a User has earned, in DTO form.
   */
  @Transactional
  public Set<Map<String, Object>> getAllBadgesByUserId(Long userId) {
    Set<UserBadge> userBadges = userBadgeRepository.findByUserId(userId);
    return userBadges.stream()
        .map(
            userBadge -> {
              Map<String, Object> badgeData = new HashMap<>();
              badgeData.put("user", userBadge.getUser());
              badgeData.put("badge", userBadge.getBadge());
              badgeData.put("dateEarned", userBadge.getDateEarned());
              return badgeData;
            })
        .collect(Collectors.toSet());
  }

  // Possibly need a method that retrieves the 3 most recent badges of a user,
  // which will display on the front of their profile-page
  // Which could use a dateEarned field in the badge-user connection.

  /**
   * Awards a Badge of badgeId to a User of userId
   *
   * @param userId (Long): The User's id (who is earning the Badge)
   * @param badgeId (Long): The Badge's id (the Badge being awarded)
   * @throws NotFoundException If either the User or Badge id is not found.
   */
  @Transactional
  public void giveUserBadge(Long userId, Long badgeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new NotFoundException("Badge with ID " + badgeId + " not found."));

    // Create a new UserBadge instance and set dateTimeEarned to the current date and time
    UserBadge userBadge = new UserBadge();
    userBadge.setUser(user);
    userBadge.setBadge(badge);
    userBadge.setDateEarned(LocalDateTime.now());

    // Save the userBadge entity
    userBadgeRepository.save(userBadge);
  }

  /**
   * Removes a user's badge, given the User and Badge id's.
   *
   * @param userId (Long): The User's id (who is losing the Badge)
   * @param badgeId (Long): The Badge's id (the Badge being removed)
   * @throws NotFoundException If either the User or Badge id is not found.
   */
  @Transactional
  public void removeUserBadge(Long userId, Long badgeId) {
    UserBadge userBadge =
        userBadgeRepository
            .findByUserIdAndBadgeId(userId, badgeId)
            .orElseThrow(() -> new NotFoundException("UserBadge not found"));

    userBadgeRepository.delete(userBadge);
  }

  /**
   * Retrieves all users that have acquired a certain badge
   *
   * @param badgeId (Long): The badge id being researched.
   * @return an ArrayList of Users that have earned the badge.
   * @throws NotFoundException When the badge id is not found in the database.
   */
  @Transactional
  public List<Map<String, Object>> getUsersByBadge(Long badgeId) {
    List<UserBadge> userBadges = userBadgeRepository.findByBadgeId(badgeId);
    return userBadges.stream()
        .map(
            userBadge -> {
              Map<String, Object> userData = new HashMap<>();
              userData.put("user", userBadge.getUser());
              userData.put("badge", userBadge.getBadge());
              userData.put("dateEarned", userBadge.getDateEarned());
              return userData;
            })
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteBadgeWithAssociatedUserBadges(Long badgeId) {
    // Retrieve the badge
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new NotFoundException("Badge with ID " + badgeId + " not found."));

    // Retrieve all user_badges associated with the badge
    List<UserBadge> associatedUserBadges = userBadgeRepository.findByBadgeId(badgeId);

    // Delete each associated user_badges record
    for (UserBadge userBadge : associatedUserBadges) {
      userBadgeRepository.delete(userBadge);
    }

    // Once all associated user_badges records are deleted, delete the badge
    badgeRepository.delete(badge);
  }
}
