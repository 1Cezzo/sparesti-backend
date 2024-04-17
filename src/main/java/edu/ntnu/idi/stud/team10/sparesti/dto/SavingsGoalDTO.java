package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
/** A DTO for the SavingsGoal entity. */
public class SavingsGoalDTO {
  private Long id;
  private String name;
  private double targetAmount;
  private double savedAmount;
  private LocalDate deadline;
  private boolean completed;
  private User user;

  public SavingsGoalDTO(SavingsGoal savingsGoal) {
    this.id = savingsGoal.getId();
    this.name = savingsGoal.getName();
    this.targetAmount = savingsGoal.getTargetAmount();
    this.savedAmount = savingsGoal.getSavedAmount();
    this.deadline = savingsGoal.getDeadline();
    this.completed = savingsGoal.isCompleted();
    this.user = savingsGoal.getUser();
  }

  public SavingsGoalDTO(String name, double targetAmount, LocalDate deadline) {
    this.name = name;
    this.targetAmount = targetAmount;
    this.savedAmount = 0;
    this.deadline = deadline;
    this.completed = false;
    this.user = null;
  }

  /**
   * Converts the DTO to a SavingsGoal entity.
   *
   * @return (SavingsGoal) The SavingsGoal entity.
   */
  public SavingsGoal toEntity() {
    SavingsGoal savingsGoal = new SavingsGoal();
    savingsGoal.setId(this.id);
    savingsGoal.setName(this.name);
    savingsGoal.setTargetAmount(this.targetAmount);
    savingsGoal.setSavedAmount(this.savedAmount);
    savingsGoal.setDeadline(this.deadline);
    savingsGoal.setCompleted(this.completed);
    savingsGoal.setUser(this.user);
    return savingsGoal;
  }
}
