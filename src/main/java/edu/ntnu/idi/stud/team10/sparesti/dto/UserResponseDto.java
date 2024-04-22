package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
  private String displayName;
  private String firstName;
  private String lastName;
  private String email;
  private String pictureUrl;
  private List<String> badges;
  private double totalSavings;
}
