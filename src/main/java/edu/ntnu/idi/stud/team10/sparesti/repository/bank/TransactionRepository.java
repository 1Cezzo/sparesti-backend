package edu.ntnu.idi.stud.team10.sparesti.repository.bank;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
