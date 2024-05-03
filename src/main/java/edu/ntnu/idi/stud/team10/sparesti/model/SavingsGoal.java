package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A savings goal is a goal that a user sets for themselves to save up for. It has a name, a target
 * amount, and a deadline.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "savings_goals")
public class SavingsGoal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "target_amount", nullable = false)
  private double targetAmount;

  @Column(name = "amount_saved", nullable = false)
  private double savedAmount;

  @Column(name = "media_url")
  private String mediaUrl;

  @Column(name = "deadline", nullable = false)
  private LocalDate deadline;

  @Column(name = "completed", nullable = false)
  private boolean completed;

  @ManyToMany(mappedBy = "userSavingsGoals")
  @JsonBackReference
  @Hidden
  private List<User> users;

  @Column(name = "author_id", nullable = false)
  private Long authorId;
}
