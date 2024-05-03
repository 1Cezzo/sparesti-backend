package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

/** Entity representing a user in the database. */
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String password;

  @Column(unique = true)
  private String email;

  @Column(nullable = true)
  private Integer loginStreak;

  @Column(nullable = true)
  private LocalDate lastLogin;

  @Column private Double totalSavings;

  @Column() private String profilePictureUrl;

  @Column() private Long checkingAccountNr;

  @Column() private Long savingsAccountNr;

  @ManyToMany
  @JoinTable(
      name = "user_savings_goal",
      joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "savings_goals_id", referencedColumnName = "id"))
  private List<SavingsGoal> userSavingsGoals;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_challenge",
      joinColumns = @JoinColumn(name = "users_id"),
      inverseJoinColumns = @JoinColumn(name = "challenge_id"))
  private List<Challenge> challenges;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_badges",
      joinColumns = @JoinColumn(name = "users_id"),
      inverseJoinColumns = @JoinColumn(name = "badge_id"))
  private Set<Badge> earnedBadges;

  private String role;

  /**
   * Add a savings goal to the user.
   *
   * @param challenge (SavingsGoal) The savings goal to add.
   */
  public void addChallenge(Challenge challenge) {
    this.challenges.add(challenge);
  }

  /**
   * Remove a savings goal from the user.
   *
   * @param challenge (SavingsGoal) The savings goal to remove.
   */
  public void removeChallenge(Challenge challenge) {
    this.challenges.remove(challenge);
  }

  /**
   * Add a badge to the user.
   *
   * @param badge (Badge) The badge to add.
   */
  public void addBadge(Badge badge) {
    this.earnedBadges.add(badge);
  }

  /**
   * Get the earned badges of the user.
   *
   * @return (List<Badge>) The earned badges of the user.
   */
  public List<Badge> getEarnedBadges() {
    if (earnedBadges == null) {
      return List.of();
    } else {
      return List.copyOf(earnedBadges);
    }
  }

  /**
   * Remove a badge from the user.
   *
   * @param badge (Badge) The badge to remove.
   */

  public void removeBadge(Badge badge) {
    this.earnedBadges.remove(badge);
  }

  /**
   * Equals method for the user.
   *
   * @param o (Object) The object to compare to.
   * @return (boolean) True if the objects are equal, false otherwise.
   */

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(password, user.password)
        && Objects.equals(email, user.email);
  }

  /**
   * Hash code method for the user.
   *
   * @return (int) The hash code of the user.
   */

  @Override
  public int hashCode() {
    return Objects.hash(id, password, email);
  }

  /**
   * String representation of the user.
   *
   * @return (String) The string representation of the user.
   */
  @Override
  public String toString() {
    return "User{" + "id=" + id + ", email='" + email + '\'' + '}';
  }
}
