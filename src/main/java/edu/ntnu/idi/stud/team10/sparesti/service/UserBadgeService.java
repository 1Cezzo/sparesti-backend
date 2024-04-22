package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Badge entities that are connected to a user. */
@Service
public class UserBadgeService {

  private final BadgeRepository badgeRepository;
  private final UserRepository userRepository;

  @Autowired
  public UserBadgeService(BadgeRepository badgeRepository, UserRepository userRepository) {
    this.badgeRepository = badgeRepository;
    this.userRepository = userRepository;
  }

  /**
   * Returns a Set of all the badges earned by a user, as DTOs.
   *
   * @param userId (Long): The User's unique ID.
   * @return A Set of all Badges that a User has earned, in DTO form.
   */
  public Set<BadgeDto> getAllBadgesByUserId(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    Set<BadgeDto> badges =
        user.getEarnedBadges().stream().map(BadgeDto::new).collect(Collectors.toSet());
    return badges;
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
    user.addBadge(badge);
    badge.addUser(user);
    userRepository.save(user);
    badgeRepository.save(badge);
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
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new NotFoundException("Badge with ID " + badgeId + " not found."));
    user.removeBadge(badge);
    badge.removeUser(user);
    userRepository.save(user);
  }

  /**
   * Retrieves all users that have acquired a certain badge
   *
   * @param badgeId (Long): The badge id being researched.
   * @return an ArrayList of Users that have earned the badge.
   * @throws NotFoundException When the badge id is not found in the database.
   */
  public List<User> getUsersByBadge(Long badgeId) {
    Badge badge =
        badgeRepository
            .findById(badgeId)
            .orElseThrow(() -> new NotFoundException("Badge with ID " + badgeId + " not found."));
    return new ArrayList<>(badge.getUsers()); // possible null exception
  }
}
