package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.ntnu.idi.stud.team10.sparesti.model.User;

/** Repository for User entities. */
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Find a User by its email.
   *
   * @param email The email of the User.
   * @return The User with the given email, if it exists.
   */
  Optional<User> findByEmail(String email);

  /**
   * Counts the total number of User entities in the database.
   *
   * @return The total number of User entities.
   */
  long count();

  /**
   * Counts the number of distinct users who have earned a specific badge.
   *
   * <p>This method is used to determine the rarity of a badge. The rarity is determined by the
   * number of unique users who have earned the badge. The method could potentially be moved to the
   * BadgeRepository.
   *
   * @param badgeId The ID of the badge.
   * @return The number of distinct users who have earned the badge.
   */
  @Query("SELECT COUNT(DISTINCT u) FROM users u JOIN u.earnedBadges b WHERE b.id = :badgeId")
  long countByBadgeId(
      long badgeId); // currently used for finding badge rarity. also could be in badgeRepo.
}
