package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import lombok.*;

/** A DTO for the SavingsGoal entity. */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavingsGoalDto {
  private Long id;
  private String name;
  private double targetAmount;
  private double savedAmount;
  private String mediaUrl;
  private LocalDate deadline;
  private boolean completed;
  private Long authorId;
}
