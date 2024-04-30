package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.AccountMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.Account;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.AccountRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.TransactionRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ConflictException;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
import edu.ntnu.idi.stud.team10.sparesti.util.UnauthorizedException;
import jakarta.transaction.Transactional;

/** Service for bank operations. */
@Service
public class BankService {
  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final AccountMapper accountMapper;
  private final TransactionMapper transactionMapper;

  @Autowired
  public BankService(
      AccountRepository accountRepository,
      TransactionRepository transactionRepository,
      AccountMapper accountMapper,
      TransactionMapper transactionMapper) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.accountMapper = accountMapper;
    this.transactionMapper = transactionMapper;
  }

  /**
   * Create a new account.
   *
   * @param accountDto (AccountDto) The account details.
   * @return The created account details.
   * @throws IllegalArgumentException If the account parameter is null.
   */
  public AccountDto createAccount(AccountDto accountDto) {
    if (accountDto == null) {
      throw new IllegalArgumentException("Account parameter cannot be null");
    }
    if (accountRepository.existsByAccountNr(accountDto.getAccountNr())) {
      throw new ConflictException("Account number already exists");
    }
    Account account = accountMapper.toEntity(accountDto);
    account.setId(null);
    account.setBalance(0);
    accountRepository.save(account);
    return accountMapper.toDto(account);
  }

  /**
   * Get account details.
   *
   * @param accountNr (int) The account number to get details for.
   * @return A Dto with the account details.
   * @throws NotFoundException If the account is not found.
   */
  public AccountDto getAccountDetails(int accountNr, Long userId) {
    Account account = findAccountByAccountNr(accountNr);
    if (!account.getOwnerId().equals(userId)) {
      throw new UnauthorizedException("User does not have access to this account");
    }
    return accountMapper.toDto(account);
  }

  /**
   * Get all accounts for a user.
   *
   * @param userId (Long) The user id to get accounts for.
   * @return The account details for all the accounts owned by the user.
   */
  public Set<AccountDto> getUserAccounts(Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("User id parameter cannot be null");
    }
    return accountRepository.findAllByOwnerId(userId).stream()
        .map(accountMapper::toDto)
        .collect(Collectors.toSet());
  }

  /**
   * Adds a transaction to an account.
   *
   * @param transactionDto (TransactionDto) The transaction details.
   * @throws NotFoundException If the account is not found.
   * @throws IllegalArgumentException If the transaction parameter is null.
   */
  @Transactional
  public void addTransaction(TransactionDto transactionDto) {
    if (transactionDto == null) {
      throw new IllegalArgumentException("Transaction parameter cannot be null");
    }
    Account account =
        accountRepository
            .findByAccountNrWithLock(transactionDto.getAccountNr())
            .orElseThrow(() -> new NotFoundException("Account not found"));
    account.alterBalance(transactionDto.getAmount());
    accountRepository.save(account);

    Transaction transaction = transactionMapper.toEntity(transactionDto);
    transaction.setAccount(account);
    transactionRepository.save(transaction);
  }

  /**
   * Find an account by account number.
   *
   * @param accountNr (int) The account number to search for.
   * @return (Account) The account entity.
   * @throws NotFoundException If the account is not found.
   */
  private Account findAccountByAccountNr(int accountNr) {
    return accountRepository
        .findByAccountNr(accountNr)
        .orElseThrow(() -> new NotFoundException("Account not found"));
  }

  /**
   * Transfers money between two accounts by creating two transactions.
   *
   * @param fromAccountNr the accountNr that is sending money.
   * @param toAccountNr the accountNr that is receiving money.
   * @param amount the amount of money being transferred.
   * @throws IllegalArgumentException if an attempt is made to transfer a negative amount.
   */
  @Transactional
  public void transferMoney(
      Integer fromAccountNr, Integer toAccountNr, double amount, Long ownerId) {
    if (amount < 0) {
      throw new IllegalArgumentException("Cannot transfer a negative amount.");
    }
    Account fromAccount = findAccountByAccountNr(fromAccountNr);
    Account toAccount = findAccountByAccountNr(toAccountNr);
    if (!(fromAccount.getOwnerId().equals(ownerId) && toAccount.getOwnerId().equals(ownerId))) {
      throw new UnauthorizedException("User does not have access to one or both of these accounts");
    }

    TransactionDto fromTransactionDto = new TransactionDto();
    fromTransactionDto.setAmount(-amount);
    fromTransactionDto.setCategory("Transfer");
    fromTransactionDto.setDescription("Transferred to account: " + toAccountNr);
    fromTransactionDto.setAccountNr(fromAccountNr);
    fromTransactionDto.setDate(LocalDate.now());
    addTransaction(fromTransactionDto);

    TransactionDto toTransactionDto = new TransactionDto();
    toTransactionDto.setAmount(amount);
    toTransactionDto.setCategory("Transfer");
    toTransactionDto.setDescription("Transferred from account: " + fromAccountNr);
    toTransactionDto.setAccountNr(toAccountNr);
    toTransactionDto.setDate(LocalDate.now());
    addTransaction(toTransactionDto);
  }

  /**
   * Get all transactions for an account.
   *
   * @param userId (Long) The user id to get transactions for.
   * @return The transactions for all the accounts owned by the user.
   */
  public Set<Transaction> getTransactionsByUserId(Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("User id parameter cannot be null");
    }
    Set<Account> accounts = accountRepository.findAllByOwnerId(userId);

    Set<Transaction> transactions = new HashSet<>();

    for (Account account : accounts) {
      transactions.addAll(transactionRepository.findByAccount(account));
    }
    return transactions;
  }

  /**
   * Gets transactions from an account that happened within the last 30 days
   *
   * @param accountNr (Integer): The account being checked
   * @return Set&lt;TransactionDto&gt; of all transactions from the account in the last 30 days.
   */
  public Set<TransactionDto> getRecentTransactionsByAccountNr(Integer accountNr, Long userId) {
    LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
    Account account = findAccountByAccountNr(accountNr);
    if (!account.getOwnerId().equals(userId)) {
      throw new UnauthorizedException("User does not have access to this account");
    }

    return account.getTransactions().stream()
        .filter(t -> !t.getDate().isBefore(thirtyDaysAgo))
        .map(transactionMapper::toDto)
        .collect(Collectors.toSet());
  }

  /**
   * Gets a list of all transactions by a singular account number
   *
   * @param accountNr (Integer) The accountNr being checked
   * @return (ResponseEntity&lt;Set&lt;TransactionDto&gt; &gt;) Set of all transactions by the
   *     account.
   */
  public Set<TransactionDto> getTransactionsByAccountNr(Integer accountNr, Long userId) {
    Account account = findAccountByAccountNr(accountNr);
    if (!account.getOwnerId().equals(userId)) {
      throw new UnauthorizedException("User does not have access to this account");
    }
    return account.getTransactions().stream()
        .map(transactionMapper::toDto)
        .collect(Collectors.toSet());
  }


  /**
   * Gets an accounts total spendings in the various categories (in transactions) from the last 30 days.
   *
   * @param accountNr (Integer) the unique number of the account.
   * @param userId (Long) the user's id.
   * @return (Map&lt;String, Double&gt;) Detailing a category, and how much was spent in it in the last 30 days.
   */
  public Map<String, Double> getSpendingsInCategories(Integer accountNr, Long userId) {
    LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
    Account account = findAccountByAccountNr(accountNr);
    if (!account.getOwnerId().equals(userId)) {
      throw new UnauthorizedException("User does not have access to this account");
    }
    // TODO: fix some minor edge-case things like when user has no transactions in past 30 days.
    Set<Transaction> transactions = transactionRepository.findByAccount(account).stream()
            .filter(t -> t.getDate().isAfter(LocalDate.now().minusDays(30))).collect(Collectors.toSet());

    return transactions.stream().collect(Collectors.groupingBy(Transaction::getCategory,
            Collectors.summingDouble(Transaction::getAmount)));
  }

  /**
   * Checks if a user can legally access an account. The user is allowed to access an account if it
   * does not exist yet.
   *
   * @param accountNr (Integer) The account number
   * @param userId (Long) The user id
   * @return {@code false} only if the account is registered with another userId as owned, {@code
   *     true} otherwise.
   */
  public boolean userHasAccessToAccount(Integer accountNr, Long userId) {
    Account account = accountRepository.findByAccountNr(accountNr).orElse(null);
    return account == null || account.getOwnerId().equals(userId);
  }

  /**
   * Checks if an account exits.
   *
   * @param accountNr (Integer) The account number
   * @return {@code true} if the account exists, {@code false} otherwise.
   */
  public boolean accountExists(Integer accountNr) {
    return accountRepository.existsByAccountNr(accountNr);
  }
}
