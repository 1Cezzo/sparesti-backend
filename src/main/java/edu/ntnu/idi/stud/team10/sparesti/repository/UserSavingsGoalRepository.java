package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserSavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.util.UserSavingsGoalId;

/** Repository for the UserSavingsGoal entity. */
public interface UserSavingsGoalRepository
    extends JpaRepository<UserSavingsGoal, UserSavingsGoalId> {
  /**
   * Find a UserSavingsGoal by its user and savings goal.
   *
   * @param user The user of the UserSavingsGoal.
   * @param savingsGoal The savings goal of the UserSavingsGoal.
   * @return The UserSavingsGoal with the given user and savings goal, if it exists.
   */
  Optional<UserSavingsGoal> findByUserAndSavingsGoal(User user, SavingsGoal savingsGoal);

  /**
   * Find all UserSavingsGoals for a user.
   *
   * @param userId The user id.
   * @return A list of UserSavingsGoals.
   */
  List<UserSavingsGoal> findByUserId(Long userId);

  /**
   * Find all UserSavingsGoals for a savings goal.
   *
   * @param savingsGoal The savings goal.
   * @return A list of UserSavingsGoals.
   */
  List<UserSavingsGoal> findBySavingsGoal(SavingsGoal savingsGoal);
}
