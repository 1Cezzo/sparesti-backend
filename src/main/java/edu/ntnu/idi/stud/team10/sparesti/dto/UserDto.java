package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.List;
import java.util.stream.Collectors;

import edu.ntnu.idi.stud.team10.sparesti.model.User;
import lombok.*;

/** Data transfer object for User entities. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private Long id;
  private String password;
  private String email;
  private String profilePictureUrl;
  private Integer checkingAccountNr;
  private Integer savingsAccountNr;
  private Double totalSavings;
  private List<ChallengeDTO> challenges;

  /**
   * Constructor for converting User entity to UserDto. Does not include password.
   *
   * @param user (User) The user to convert.
   */
  public UserDto(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.profilePictureUrl = user.getProfilePictureUrl();
    this.checkingAccountNr = user.getCheckingAccountNr();
    this.savingsAccountNr = user.getSavingsAccountNr();
    this.totalSavings = user.getTotalSavings();
    if (user.getChallenges() != null) {
      this.challenges =
          user.getChallenges().stream().map(ChallengeDTO::new).collect(Collectors.toList());
    } else {
      this.challenges = null;
    }
  }
}
