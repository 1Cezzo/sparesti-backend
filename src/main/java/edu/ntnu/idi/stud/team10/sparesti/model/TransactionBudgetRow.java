package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

/** A table to keep track of the transactions that are associated with a budget row. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "TransactionBudgetRow")
public class TransactionBudgetRow {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne private BudgetRow budgetRow;
  @OneToMany private Set<Transaction> transactions = new HashSet<>();

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
