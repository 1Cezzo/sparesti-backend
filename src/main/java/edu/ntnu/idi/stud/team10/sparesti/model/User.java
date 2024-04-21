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
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String displayName;

  @Column() private String firstName;

  @Column() private String lastName;

  @Column(nullable = false)
  private String password;

  @Column(unique = true)
  private String email;

  @Column() private String profilePictureUrl;

  @Column() private int checkingAccountNr;

  @Column() private int savingsAccountNr;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<SavingsGoal> savingsGoals;

  @ManyToMany
  @JoinTable(
      name = "userBadges",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "badge_id"))
  private Set<Badge> earnedBadges;

  /**
   * Constructor for converting UserDto to User.
   *
   * @param dto (UserDto) A Dto representing the user.
   */
  public User(UserDto dto) {
    this.id = dto.getId();
    this.displayName = dto.getDisplayName();
    this.password = dto.getPassword();
    this.email = dto.getEmail();
    this.profilePictureUrl = dto.getProfilePictureUrl();
  }
}
