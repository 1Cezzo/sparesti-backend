package edu.ntnu.idi.stud.team10.sparesti.util;

import java.io.Serializable;

import lombok.Data;

/** Composite key for the UserSavingsGoal entity. */
@Data
public class UserSavingsGoalId implements Serializable {

  private Long user;
  private Long savingsGoal;
}
