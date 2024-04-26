package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDateTime;

import edu.ntnu.idi.stud.team10.sparesti.util.UserSavingsGoalId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_savings_goal")
@IdClass(UserSavingsGoalId.class)
/** Represents a user's savings goal. */
public class UserSavingsGoal {

  @Id
  @ManyToOne
  @JoinColumn(name = "users_id")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "savings_goals_id")
  private SavingsGoal savingsGoal;

  @Column(name = "last_contributed")
  private LocalDateTime lastContributed;

  @Column(name = "contribution_amount")
  private double contributionAmount;
}
