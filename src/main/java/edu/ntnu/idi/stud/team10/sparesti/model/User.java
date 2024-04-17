package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

/** Entity representing a user in the database. */
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;
  private String email;
  private String profilePictureUrl;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<SavingsGoal> savingsGoals;

  @ManyToMany
  @JoinTable(
          name = "userBadges",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "badge_id")) //might need to give more descriptive column names
  private Set<Badge> earnedBadges;

  /**
   * Constructor for converting UserDto to User.
   *
   * @param dto (UserDto) A Dto representing the user.
   */
  public User(UserDto dto) {
    this.id = dto.getId();
    this.username = dto.getUsername();
    this.password = dto.getPassword();
    this.email = dto.getEmail();
    this.profilePictureUrl = dto.getProfilePictureUrl();
  }
}
