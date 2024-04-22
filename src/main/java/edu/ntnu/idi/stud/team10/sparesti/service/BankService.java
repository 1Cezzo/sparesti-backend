package edu.ntnu.idi.stud.team10.sparesti.service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;
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
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
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
  public AccountDto getAccountDetails(int accountNr) {
    Account account = findAccountByAccountNr(accountNr);
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
   * Creates and returns a random transaction to an account.
   * Intended for creation of mock data for bank.
   *
   * @param accountNr number of the account (who the transaction is coming from)
   * @return a randomly generated transaction
   */
  private TransactionDto generateRandomPurchase(Long accountNr) {
    // method should be simple enough to be moved anywhere else where it fits better.
    // TODO: move into TransactionDto as a public static method?
    DecimalFormat df = new DecimalFormat("#.##");
    TransactionDto transactionDto = new TransactionDto();
    Random random = new Random();
    transactionDto.setAmount(Double.parseDouble(df.format(random.nextDouble(-500, -20))));
    //bounds of transaction could be moved to args for this method, which would allow for more dynamic transactions.
    transactionDto.setCategory(CategoryEnum.getRandomCategory());
    transactionDto.setAccountNr(Integer.parseInt(String.valueOf(accountNr)));
    return transactionDto;
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
   * The transactions are of type "NONE"
   *
   * @param fromAccountNr
   * @param toAccountNr
   * @param amount
   */
  public void transferMoney(Integer fromAccountNr, Integer toAccountNr, double amount) {
    if (amount < 0 ) {
      throw new IllegalArgumentException(
              "Cannot transfer a negative amount.");
    }

    TransactionDto fromTransactionDto = new TransactionDto();
    fromTransactionDto.setAmount(-amount);
    fromTransactionDto.setCategory(CategoryEnum.NONE);
    fromTransactionDto.setAccountNr(fromAccountNr);
    addTransaction(fromTransactionDto);

    TransactionDto toTransactionDto = new TransactionDto();
    toTransactionDto.setAmount(amount);
    toTransactionDto.setCategory(CategoryEnum.NONE);
    toTransactionDto.setAccountNr(toAccountNr);
    addTransaction(toTransactionDto);
  }
}
