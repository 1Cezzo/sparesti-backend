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

  /**
   * Constructor for creating a new SavingsGoalDTO.
   *
   * @param name The name of the savings goal.
   * @param targetAmount The target amount of the savings goal.
   * @param mediaUrl The media URL of the savings goal.
   * @param deadline The deadline of the savings goal.
   * @param authorId The ID of the author of the savings goal.
   */
  public SavingsGoalDto(
      String name, double targetAmount, String mediaUrl, LocalDate deadline, Long authorId) {
    this.name = name;
    this.targetAmount = targetAmount;
    this.savedAmount = 0;
    this.mediaUrl = mediaUrl;
    this.deadline = deadline;
    this.completed = false;
    this.authorId = authorId;
  }
}
