package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDtoTest {

  private AccountDto accountDto1;
  private AccountDto accountDto2;

  @BeforeEach
  public void setUp() {
    accountDto1 = new AccountDto();
    accountDto1.setId(1L);
    accountDto1.setOwnerId(123L);
    accountDto1.setAccountNr(456789L);
    accountDto1.setName("Test Account");
    accountDto1.setBalance(1000.0);

    // Adding transactions for testing
    Set<TransactionDto> transactions = new HashSet<>();
    transactions.add(
        new TransactionDto(1L, 100.0, "Description1", "Category1", 1L, LocalDate.now()));
    transactions.add(
        new TransactionDto(2L, -50.0, "Description2", "Category2", 1L, LocalDate.now()));
    accountDto1.setTransactions(transactions);

    accountDto2 = new AccountDto();
    accountDto2.setId(1L);
    accountDto2.setOwnerId(123L);
    accountDto2.setAccountNr(456789L);
    accountDto2.setName("Test Account");
    accountDto2.setBalance(1000.0);
    accountDto2.setTransactions(transactions);
  }

  @Test
  public void testAccountDtoFields() {
    assertEquals(1L, accountDto1.getId());
    assertEquals(123L, accountDto1.getOwnerId());
    assertEquals(456789, accountDto1.getAccountNr());
    assertEquals("Test Account", accountDto1.getName());
    assertEquals(1000.0, accountDto1.getBalance());
  }

  @Test
  public void testAccountDtoTransactions() {
    assertNotNull(accountDto1.getTransactions());
    assertEquals(2, accountDto1.getTransactions().size());
  }

  @Test
  public void testEquals() {
    assertTrue(accountDto1.equals(accountDto2));
  }

  @Test
  public void testNotEquals() {
    accountDto2.setOwnerId(456L);
    assertFalse(accountDto1.equals(accountDto2));
  }

  @Test
  public void testHashCode() {
    assertEquals(accountDto1.hashCode(), accountDto2.hashCode());
  }
}
