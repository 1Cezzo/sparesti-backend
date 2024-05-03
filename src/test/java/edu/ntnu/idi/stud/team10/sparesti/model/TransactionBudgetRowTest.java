package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionBudgetRowTest {
  private TransactionBudgetRow transactionBudgetRow;
  private BudgetRow budgetRow;
  private Transaction transaction;

  @BeforeEach
  public void setUp() {
    transactionBudgetRow = new TransactionBudgetRow();
    transactionBudgetRow.setId(1L);
    budgetRow = new BudgetRow();
    budgetRow.setId(1L);
    budgetRow.setUsedAmount(1.0);
    budgetRow.setMaxAmount(5.0);
    transactionBudgetRow.setBudgetRow(budgetRow);
    transaction = new Transaction();
    transaction.setId(1L);
    HashSet<Transaction> transactions = new HashSet<>();
    transactions.add(transaction);
    transactionBudgetRow.setTransactions(transactions);
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      transactionBudgetRow.setId(2L);
      assertEquals(2L, transactionBudgetRow.getId());
    }

    @Test
    void getAndSetBudgetRow() {
      BudgetRow newBudgetRow = new BudgetRow();
      newBudgetRow.setId(2L);
      transactionBudgetRow.setBudgetRow(newBudgetRow);
      assertEquals(newBudgetRow, transactionBudgetRow.getBudgetRow());
    }

    @Test
    void getAndSetTransactions() {
      Transaction newTransaction = new Transaction();
      newTransaction.setId(2L);
      HashSet<Transaction> newTransactions = new HashSet<>();
      newTransactions.add(newTransaction);
      transactionBudgetRow.setTransactions(newTransactions);
      assertEquals(newTransactions, transactionBudgetRow.getTransactions());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private TransactionBudgetRow anotherTransactionBudgetRow;

    @BeforeEach
    void setUp() {
      anotherTransactionBudgetRow = new TransactionBudgetRow();
      anotherTransactionBudgetRow.setId(1L);
      anotherTransactionBudgetRow.setBudgetRow(budgetRow);
      Transaction anotherTransaction = new Transaction();
      anotherTransaction.setId(1L);
      HashSet<Transaction> anotherTransactions = new HashSet<>();
      anotherTransactions.add(anotherTransaction);
      anotherTransactionBudgetRow.setTransactions(anotherTransactions);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(transactionBudgetRow, anotherTransactionBudgetRow);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherTransactionBudgetRow.setId(2L);
      assertNotEquals(transactionBudgetRow, anotherTransactionBudgetRow);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(transactionBudgetRow.hashCode(), anotherTransactionBudgetRow.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherTransactionBudgetRow.setId(2L);
      assertNotEquals(transactionBudgetRow.hashCode(), anotherTransactionBudgetRow.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(transactionBudgetRow.toString());
  }
}
