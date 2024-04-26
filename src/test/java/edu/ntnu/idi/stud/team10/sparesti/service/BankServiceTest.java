package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.AccountMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.Account;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.AccountRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.TransactionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankServiceTest {

  @Mock private AccountRepository accountRepository;

  @Mock private TransactionRepository transactionRepository;

  @Mock private AccountMapper accountMapper;

  @Mock private TransactionMapper transactionMapper;

  private BankService bankService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    bankService =
        new BankService(accountRepository, transactionRepository, accountMapper, transactionMapper);
  }

  @Test
  void testCreateAccount() {
    AccountDto accountDto = new AccountDto();
    Account account = new Account();
    when(accountMapper.toEntity(any())).thenReturn(account);
    when(accountRepository.save(any())).thenReturn(account);
    when(accountMapper.toDto(any())).thenReturn(accountDto);

    AccountDto result = bankService.createAccount(accountDto);

    assertNotNull(result);
    verify(accountMapper, times(1)).toEntity(accountDto);
    verify(accountRepository, times(1)).save(account);
    verify(accountMapper, times(1)).toDto(account);
  }

  @Test
  void testGetAccountDetails() {
    Account account = new Account();
    account.setOwnerId(1L);
    AccountDto accountDto = new AccountDto();
    when(accountRepository.findByAccountNr(anyInt())).thenReturn(java.util.Optional.of(account));
    when(accountMapper.toDto(any())).thenReturn(accountDto);

    AccountDto result = bankService.getAccountDetails(1, 1L);

    assertNotNull(result);
    verify(accountRepository, times(1)).findByAccountNr(1);
    verify(accountMapper, times(1)).toDto(account);
  }

  @Test
  void testGetUserAccounts() {
    Set<Account> accounts = new HashSet<>();
    accounts.add(new Account());
    when(accountRepository.findAllByOwnerId(anyLong())).thenReturn(accounts);
    when(accountMapper.toDto(any())).thenReturn(new AccountDto());

    Set<AccountDto> result = bankService.getUserAccounts(1L);

    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(accountRepository, times(1)).findAllByOwnerId(1L);
    verify(accountMapper, times(1)).toDto(any());
  }

  @Test
  void testGetTransactionsByUserId() {
    Set<Account> accounts = new HashSet<>();
    accounts.add(new Account());
    when(accountRepository.findAllByOwnerId(anyLong())).thenReturn(accounts);
    when(transactionRepository.findByAccount(any())).thenReturn(new HashSet<>());

    Set<Transaction> result = bankService.getTransactionsByUserId(1L);

    assertNotNull(result);
    verify(accountRepository, times(1)).findAllByOwnerId(1L);
    verify(transactionRepository, times(1)).findByAccount(any());
  }

  @Test
  void testAddTransaction() {
    TransactionDto transactionDto = new TransactionDto();
    transactionDto.setAccountNr(1);
    transactionDto.setAmount(100);
    Account account = new Account();
    when(accountRepository.findByAccountNrWithLock(anyInt()))
        .thenReturn(java.util.Optional.of(account));
    when(transactionMapper.toEntity(any())).thenReturn(new Transaction());

    assertDoesNotThrow(() -> bankService.addTransaction(transactionDto));
    verify(accountRepository, times(1)).findByAccountNrWithLock(transactionDto.getAccountNr());
    verify(transactionMapper, times(1)).toEntity(transactionDto);
    verify(accountRepository, times(1)).save(account);
    verify(transactionRepository, times(1)).save(any());
  }
}
