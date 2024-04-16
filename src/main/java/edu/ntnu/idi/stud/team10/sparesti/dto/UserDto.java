package edu.ntnu.idi.stud.team10.sparesti.dto;

import lombok.Data;

@Data
public class UserDto {
  private Long id;
  private String username;
  private String password;
  private String email;
  private String profilePictureUrl;
}
