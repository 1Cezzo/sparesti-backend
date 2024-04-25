package edu.ntnu.idi.stud.team10.sparesti.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionTest {

  private Transaction transaction;
  private final double testAmount = 100.0;
  private Account testAccount;

  @BeforeEach
  public void setUp() {
    transaction = new Transaction();
    transaction.setAmount(testAmount);
    testAccount = new Account(); // Instantiate test account as needed
    transaction.setAccount(testAccount);
  }

  @Test
  public void testTransactionAttributes() {
    assertNotNull(transaction);
    assertEquals(testAmount, transaction.getAmount());
    assertEquals(testAccount, transaction.getAccount());
  }

  @Test
  public void testEqualsAndHashCode() {
    Transaction sameTransaction = new Transaction();
    sameTransaction.setAmount(testAmount);
    sameTransaction.setAccount(testAccount);

    assertTrue(transaction.equals(sameTransaction));
    assertEquals(transaction.hashCode(), sameTransaction.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString = "Transaction{id=null, amount=100.0, account=null}";
    assertEquals(expectedToString, transaction.toString());
  }

  // Add more tests as needed...

}
