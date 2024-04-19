package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountDtoTest {

  private AccountDto account;

  @BeforeEach
  public void setUp() {
    account = new AccountDto();
    account.setId(1L);
    account.setOwnerId(123L);
    account.setAccountNr(456789);
    account.setName("Test Account");
    account.setBalance(1000.0);

    // Adding transactions for testing
    Set<TransactionDto> transactions = new HashSet<>();
    transactions.add(new TransactionDto(1L, 100.0, 1));
    transactions.add(new TransactionDto(2L, -50.0, 1));
    account.setTransactions(transactions);
  }

  @Test
  public void testAccountDtoFields() {
    assertEquals(1L, account.getId());
    assertEquals(123L, account.getOwnerId());
    assertEquals(456789, account.getAccountNr());
    assertEquals("Test Account", account.getName());
    assertEquals(1000.0, account.getBalance());
  }

  @Test
  public void testAccountDtoTransactions() {
    assertNotNull(account.getTransactions());
    assertEquals(2, account.getTransactions().size());
  }
}
