package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDateTime;

import edu.ntnu.idi.stud.team10.sparesti.util.UserBadgeId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_badges")
@IdClass(UserBadgeId.class)
public class UserBadge {

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "badge_id")
  private Badge badge;

  @Column(name = "date_earned")
  private LocalDateTime dateEarned;

  // Constructors, getters, and setters
}
