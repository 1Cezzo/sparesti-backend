package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.PasswordResetToken;

/** Repository for PasswordResetToken entities. */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  PasswordResetToken findByToken(String token);

  Optional<PasswordResetToken> findByEmail(String email);

  void deleteByEmail(String email);
}
