package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
/**
 * A challenge is a goal that a user sets for themselves to complete. It has a description, a
 * target, and a saved amount. The challenge can be of different types, such as a purchase challenge
 * or a saving challenge.
 */
public class Challenge {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "target_amount", nullable = false)
  private double targetAmount;

  @Column(name = "saved_amount", nullable = false)
  private double savedAmount;

  @Column(name = "media_url")
  private String mediaUrl;

  @Enumerated(EnumType.STRING)
  private TimeInterval timeInterval;

  @Enumerated(EnumType.STRING)
  private DifficultyLevel difficultyLevel;

  @Column(name = "expiry_date", nullable = false)
  private LocalDate expiryDate;

  @Column(name = "completed", nullable = false)
  private boolean completed;

  @ManyToMany(
      mappedBy = "challenges",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  @JsonIgnore
  private List<User> users;
}
