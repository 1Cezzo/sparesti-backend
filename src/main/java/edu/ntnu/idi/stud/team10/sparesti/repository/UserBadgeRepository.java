package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.idi.stud.team10.sparesti.model.UserBadge;

/** Repository for UserBadge. */
@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
  /**
   * Find a user badge by user id and badge id.
   *
   * @param userId the user id
   * @param badgeId the badge id
   * @return the user badge
   */
  Optional<UserBadge> findByUserIdAndBadgeId(Long userId, Long badgeId);

  /**
   * Find all user badges for a user.
   *
   * @param userId the user id
   * @return a set of user badges
   */
  Set<UserBadge> findByUserId(Long userId);

  /**
   * Find all user badges for a badge.
   *
   * @param badgeId the badge id
   * @return a list of user badges
   */
  List<UserBadge> findByBadgeId(Long badgeId);
}
