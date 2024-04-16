package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.User;

/** Repository for User entities. */
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);
}
