package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.User;

/** Repository for the Budget entity. */
public interface BudgetRepository extends JpaRepository<Budget, Long> {

  /**
   * Find all budgets for a user.
   *
   * @param userId The user id.
   * @return A list of budgets.
   */
  List<Budget> findByUserId(Long userId);

  /**
   * Find all budgets for a user.
   *
   * @param user The user.
   * @return A list of budgets.
   */
  List<Budget> findByUser(User user);
}
