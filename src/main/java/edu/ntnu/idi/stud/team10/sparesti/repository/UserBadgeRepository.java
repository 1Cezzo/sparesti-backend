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
  Optional<UserBadge> findByUserIdAndBadgeId(Long userId, Long badgeId);

  Set<UserBadge> findByUserId(Long userId);

  List<UserBadge> findByBadgeId(Long badgeId);
}
