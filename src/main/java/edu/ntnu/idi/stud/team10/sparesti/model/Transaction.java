package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Represents a transaction in an account. */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private double amount;

  // Type enum from budget goes here, need to merge first

  @ManyToOne
  @JoinColumn(nullable = false, name = "account_id", referencedColumnName = "id")
  private Account account;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return Double.compare(amount, that.amount) == 0
        && Objects.equals(id, that.id)
        && Objects.equals(account, that.account);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amount, account);
  }

  @Override
  public String toString() {
    Long accountId = account == null ? null : account.getId();
    return "Transaction{" + "id=" + id + ", amount=" + amount + ", account=" + accountId + '}';
  }
}
