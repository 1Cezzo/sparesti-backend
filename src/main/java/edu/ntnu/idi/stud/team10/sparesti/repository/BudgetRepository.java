package edu.ntnu.idi.stud.team10.sparesti.repository;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<SavingsGoal, Long> {}
