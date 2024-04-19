package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Represents a bank account for a user. */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long ownerId;

  @Column(nullable = false, unique = true)
  private int accountNr;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private double balance;

  @Column
  @OneToMany(mappedBy = "account")
  private Set<Transaction> transactions = new HashSet<>();

  /**
   * Alter the balance of the account.
   *
   * @param amount (double) The amount to alter by. Negative subtracts, positive adds.
   */
  public void alterBalance(double amount) {
    balance += amount;
  }

  /**
   * Add a transaction to the account.
   *
   * @param o the transaction to add
   * @return the account the transaction is associated with
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return accountNr == account.accountNr
        && Double.compare(balance, account.balance) == 0
        && Objects.equals(id, account.id)
        && Objects.equals(ownerId, account.ownerId)
        && Objects.equals(name, account.name);
  }

  /**
   * Returns the hash code of the account.
   *
   * @return the hash code of the account
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, ownerId, accountNr, name, balance);
  }

  /**
   * Returns a string representation of the account.
   *
   * @return a string representation of the account
   */
  @Override
  public String toString() {
    return "Account{"
        + "id="
        + id
        + ", ownerId="
        + ownerId
        + ", accountNr="
        + accountNr
        + ", name='"
        + name
        + '\''
        + ", balance="
        + balance
        + ", transactions="
        + transactions
        + '}';
  }
}
