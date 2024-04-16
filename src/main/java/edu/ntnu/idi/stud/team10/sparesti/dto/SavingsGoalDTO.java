package main.java.edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.java.edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/** A DTO for the SavingsGoal entity. */
public class SavingsGoalDTO {
  private Long id;
  private String name;
  private double targetAmount;
  private LocalDate deadline;

  public SavingsGoal toEntity() {
    return new SavingsGoal(id, name, targetAmount, deadline);
  }
}
