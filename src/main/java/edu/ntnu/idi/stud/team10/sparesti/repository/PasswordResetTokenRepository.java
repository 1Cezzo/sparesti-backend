package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.PasswordResetToken;

/** Repository for PasswordResetToken entities. */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  /**
   * Find a PasswordResetToken by its token.
   *
   * @param token The token of the PasswordResetToken.
   * @return The PasswordResetToken with the given token, if it exists.
   */
  PasswordResetToken findByToken(String token);

  /**
   * Find a PasswordResetToken by its email.
   *
   * @param email The email of the PasswordResetToken.
   * @return The PasswordResetToken with the given email, if it exists.
   */
  Optional<PasswordResetToken> findByEmail(String email);

  /**
   * Delete a PasswordResetToken by its email.
   *
   * @param email The email of the PasswordResetToken.
   */
  void deleteByEmail(String email);
}
