package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dto to represent a user savings goal entity. */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSavingsGoalDto {
  private long id;
  private Long userId;
  private String userEmail;
  private String profilePictureUrl;
  private Long savingsGoalId;
  private String savingsGoalName;
  private double contributionAmount;
  private String lastContributed;

  public UserSavingsGoalDto(
      Long id,
      String email,
      String profilePictureUrl,
      double contributionAmount,
      LocalDateTime lastContributed) {
    this.id = id;
    this.userEmail = email;
    this.profilePictureUrl = profilePictureUrl;
    this.contributionAmount = contributionAmount;
    this.lastContributed = lastContributed.toString();
  }
}
