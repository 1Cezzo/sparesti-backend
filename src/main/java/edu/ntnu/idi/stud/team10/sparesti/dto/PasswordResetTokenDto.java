package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDateTime;

import lombok.*;

/** Represents a password reset token. */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordResetTokenDto {
  private String token;
  private String email;
  private LocalDateTime expirationDateTime;
}
