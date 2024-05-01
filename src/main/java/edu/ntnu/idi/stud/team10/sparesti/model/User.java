package edu.ntnu.idi.stud.team10.sparesti.model;

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

  public void addBadge(Badge badge) {
    this.earnedBadges.add(badge);
  }

  public List<Badge> getEarnedBadges() {
    if (earnedBadges == null) {
      return List.of();
    } else {
      return List.copyOf(earnedBadges);
    }
  }

  public void removeBadge(Badge badge) {
    this.earnedBadges.remove(badge);
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(id, password, email);
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", email='" + email + '\'' + '}';
  }
}
