package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dto to represent a user badge entity. */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBadgeDto {
  private Long id;
  private Long userId;
  private Long badgeId;
  private LocalDateTime dateEarned;
}
