package edu.ntnu.idi.stud.team10.sparesti.repository;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository for Badge entities. */
public interface BadgeRepository extends JpaRepository<Badge, Long> {}
