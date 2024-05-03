package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

/**
 * A budget is created by a user to help them save money by keeping their spendings limited in a few
 * categories, that are set in a BudgetRow.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "budget")
public class Budget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "budget")
  private Set<BudgetRow> row = new HashSet<>();

  @Column private String name;

  @Column private LocalDate expiryDate;
  @Column private LocalDate creationDate;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
