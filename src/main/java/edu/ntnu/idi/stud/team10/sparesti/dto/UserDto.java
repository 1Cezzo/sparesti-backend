package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.List;
import java.util.stream.Collectors;

import edu.ntnu.idi.stud.team10.sparesti.model.User;
import lombok.*;

/** Data transfer object for User entities. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
  private Long id;
  private String username;
  private String password;
  private String email;
  private String profilePictureUrl;
  private List<SavingsGoalDTO> savingsGoals;

  /**
   * Constructor for converting User entity to UserDto. Does not include password.
   *
   * @param user (User) The user to convert.
   */
  public UserDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.profilePictureUrl = user.getProfilePictureUrl();
    this.savingsGoals =
        user.getSavingsGoals().stream().map(SavingsGoalDTO::new).collect(Collectors.toList());
  }
}
