package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.ntnu.idi.stud.team10.sparesti.model.User;

/** Repository for User entities. */
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  long count();

  @Query("SELECT COUNT(DISTINCT u) FROM User u JOIN u.earnedBadges b WHERE b.id = :badgeId")
  long countByBadgeId(
      long badgeId); // currently used for finding badge rarity. also could be in badgeRepo.
}
