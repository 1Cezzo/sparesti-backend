package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;

/** Repository for the SavingsGoal entity. */
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
  List<SavingsGoal> findSavingsGoalByAuthorId(Long userId);
}
