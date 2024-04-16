package edu.ntnu.idi.stud.team10.sparesti.repository;

import edu.ntnu.idi.stud.team10.sparesti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}


