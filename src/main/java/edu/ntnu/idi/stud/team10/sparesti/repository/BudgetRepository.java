package edu.ntnu.idi.stud.team10.sparesti.repository;

import edu.ntnu.idi.stud.team10.sparesti.model.Budget;

import edu.ntnu.idi.stud.team10.sparesti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository for the Budget entity. */
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserId(Long userId);

    List<Budget> findByUser(User user);

}
