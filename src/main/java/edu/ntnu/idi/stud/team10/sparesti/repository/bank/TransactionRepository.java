package edu.ntnu.idi.stud.team10.sparesti.repository.bank;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.Account;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;

/** Repository for Transaction entities. */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Set<Transaction> findByAccount(Account account);
}
