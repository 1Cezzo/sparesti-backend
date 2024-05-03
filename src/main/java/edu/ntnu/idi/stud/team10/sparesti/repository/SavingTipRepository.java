package edu.ntnu.idi.stud.team10.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingTip;

/** Repository for SavingTip entities. */
public interface SavingTipRepository extends JpaRepository<SavingTip, Long> {
  @Query(value = "SELECT message FROM saving_tips ORDER BY RAND() LIMIT 1", nativeQuery = true)
  String getRandomTip();
}
