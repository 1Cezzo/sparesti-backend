package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.List;
import java.util.Set;
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
  private String displayName;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String profilePictureUrl;
  private List<SavingsGoalDTO> savingsGoals;
  private List<ChallengeDTO> challenges;
  private Set<BadgeDto> badges;

  /**
   * Constructor for converting User entity to UserDto. Does not include password.
   *
   * @param user (User) The user to convert.
   */
  public UserDto(User user) {
    this.id = user.getId();
    this.displayName = user.getDisplayName();
    this.email = user.getEmail();
    this.profilePictureUrl = user.getProfilePictureUrl();
    if (user.getSavingsGoals() != null) {
      this.savingsGoals = user.getSavingsGoals().stream().map(SavingsGoalDTO::new).toList();
    } else {
      this.savingsGoals = null;
    }
    if (user.getChallenges() != null) {
      this.challenges =
          user.getChallenges().stream().map(ChallengeDTO::new).collect(Collectors.toList());
    } else {
      this.challenges = null;
    }
    if (user.getEarnedBadges() != null) {
      this.badges = user.getEarnedBadges().stream().map(BadgeDto::new).collect(Collectors.toSet());
    }
  }

  /**
   * Convert UserDto to User entity.
   *
   * @return (User) The User entity.
   */
  public User toEntity() {
    User user = new User();
    user.setId(this.id);
    user.setUsername(this.username);
    user.setPassword(this.password);
    user.setEmail(this.email);
    user.setProfilePictureUrl(this.profilePictureUrl);
    if (this.savingsGoals != null) {
      user.setSavingsGoals(
          this.savingsGoals.stream().map(SavingsGoalDTO::toEntity).collect(Collectors.toList()));
    } else {
      user.setSavingsGoals(null);
    }
    if (this.challenges != null) {
      user.setChallenges(
          this.challenges.stream().map(ChallengeDTO::toEntity).collect(Collectors.toList()));
    } else {
      user.setChallenges(null);
    }
    user.setEarnedBadges(null);
    return user;
  }
}
