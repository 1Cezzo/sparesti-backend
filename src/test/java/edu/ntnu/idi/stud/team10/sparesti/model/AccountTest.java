package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountTest {

  private Account account;

  @BeforeEach
  public void setUp() {
    account = new Account();
    account.setId(1L);
    account.setOwnerId(1L);
    account.setAccountNr(123456);
    account.setName("Test Account");
    account.setBalance(1000.0);
    account.setTransactions(new HashSet<>());
  }

  @Test
  public void testAccountFields() {
    assertEquals(1L, account.getId());
    assertEquals(1L, account.getOwnerId());
    assertEquals(123456, account.getAccountNr());
    assertEquals("Test Account", account.getName());
    assertEquals(1000.0, account.getBalance());
    assertNotNull(account.getTransactions());
  }

  @Test
  public void testAlterBalance() {
    account.alterBalance(500.0);
    assertEquals(1500.0, account.getBalance());

    account.alterBalance(-200.0);
    assertEquals(1300.0, account.getBalance());
  }

  @Test
  public void testEqualsAndHashCode() {
    Account sameAccount = new Account();
    sameAccount.setId(1L);
    sameAccount.setOwnerId(1L);
    sameAccount.setAccountNr(123456);
    sameAccount.setName("Test Account");
    sameAccount.setBalance(1000.0);
    sameAccount.setTransactions(new HashSet<>());

    assertEquals(account, sameAccount);
    assertEquals(account.hashCode(), sameAccount.hashCode());

    Account differentAccount = new Account();
    differentAccount.setId(2L);
    differentAccount.setOwnerId(1L);
    differentAccount.setAccountNr(123456);
    differentAccount.setName("Test Account");
    differentAccount.setBalance(1000.0);
    differentAccount.setTransactions(new HashSet<>());

    assertNotEquals(account, differentAccount);
    assertNotEquals(account.hashCode(), differentAccount.hashCode());
  }

  @Test
  public void testToString() {
    String expectedString =
        "Account{id=1, ownerId=1, accountNr=123456, name='Test Account', balance=1000.0, transactions=[]}";
    assertEquals(expectedString, account.toString());
  }
}
