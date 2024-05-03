package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;

/** Repository for UserInfo. */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
  /**
   * Find a UserInfo by its user id.
   *
   * @param userId The user id of the UserInfo.
   * @return The UserInfo with the given user id, if it exists.
   */
  Optional<UserInfo> findByUserId(Long userId);

  /**
   * Checks if the UserInfo exists by its user id.
   *
   * @param userId The
   * @return
   */
  boolean existsByUserId(Long userId);

  /**
   * Checks if the UserInfo exists by its display name.
   *
   * @param displayName The display name of the UserInfo.
   * @return True if the UserInfo exists, false otherwise.
   */

  boolean existsByDisplayName(String displayName);
}
