package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
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
  private String mediaUrl;
  private LocalDate deadline;
  private boolean completed;

  public SavingsGoalDTO(SavingsGoal savingsGoal) {
    this.id = savingsGoal.getId();
    this.name = savingsGoal.getName();
    this.targetAmount = savingsGoal.getTargetAmount();
    this.savedAmount = savingsGoal.getSavedAmount();
    this.mediaUrl = savingsGoal.getMediaUrl();
    this.deadline = savingsGoal.getDeadline();
    this.completed = savingsGoal.isCompleted();
  }

  public SavingsGoalDTO(String name, double targetAmount, String mediaUrl, LocalDate deadline) {
    this.name = name;
    this.targetAmount = targetAmount;
    this.savedAmount = 0;
    this.mediaUrl = mediaUrl;
    this.deadline = deadline;
    this.completed = false;
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
    savingsGoal.setMediaUrl(this.mediaUrl);
    savingsGoal.setDeadline(this.deadline);
    savingsGoal.setCompleted(this.completed);
    return savingsGoal;
  }
}
