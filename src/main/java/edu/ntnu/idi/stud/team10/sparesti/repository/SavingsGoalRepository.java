package main.java.edu.ntnu.idi.stud.team10.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.java.edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;

/** Repository for the SavingsGoal entity. */
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {}
