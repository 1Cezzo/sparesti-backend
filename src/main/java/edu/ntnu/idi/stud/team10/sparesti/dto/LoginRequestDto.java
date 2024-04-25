package edu.ntnu.idi.stud.team10.sparesti.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Represents a login request data transfer object. */
@Data
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
  private String username;
  private String password;
  private String role;

  /**
   * Constructs a new LoginRequestDTO with the specified username and password.
   *
   * @param username the username
   * @param password the password
   */
  public LoginRequestDto(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
