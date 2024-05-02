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
  Optional<UserSavingsGoal> findByUserAndSavingsGoal(User user, SavingsGoal savingsGoal);

  List<UserSavingsGoal> findByUserId(Long userId);

  List<UserSavingsGoal> findBySavingsGoal(SavingsGoal savingsGoal);

  List<SavingsGoal> findSavingsGoalByAuthorId(Long authorId);
}
