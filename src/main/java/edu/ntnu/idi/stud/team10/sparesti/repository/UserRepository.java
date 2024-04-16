package edu.ntnu.idi.stud.team10.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
}
