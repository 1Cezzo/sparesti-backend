package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for mock data creation */
@Service
public class MockDataService {
  private final UserRepository userRepository;
  private final BankService bankService;
  private final UserService userService;
  private final UserInfoService userInfoService;
  private final TransactionMapper transactionMapper;

  @Autowired
  public MockDataService(
      UserRepository userRepository,
      BankService bankService,
      UserService userService,
      UserInfoService userInfoService,
      TransactionMapper transactionMapper) {
    this.userRepository = userRepository;
    this.bankService = bankService;
    this.userService = userService;
    this.userInfoService = userInfoService;
    this.transactionMapper = transactionMapper;
  }

  /**
   * Creates and returns a random transaction to an account. Intended for creation of mock data for
   * bank.
   *
   * @param accountNr number of the account (who the transaction is coming from)
   * @return a randomly generated transaction
   */
  public TransactionDto generateRandomPurchase(Integer accountNr) {
    double amount = ThreadLocalRandom.current().nextDouble(-500, -20);
    String category = CATEGORIES.get(RANDOM.nextInt(CATEGORIES.size()));
    List<String> descriptions = CATEGORY_DESCRIPTIONS.get(category);
    String description = descriptions.get(RANDOM.nextInt(descriptions.size()));

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.setAmount(Math.round(amount * 100.0) / 100.0); // rounding to 2 decimal places
    transactionDto.setCategory(category);
    transactionDto.setDate(randomRecentDate()); // any random time in the last 30 days
    transactionDto.setDescription(description);
    transactionDto.setAccountNr(accountNr);
    return transactionDto;
  }

  /**
   * Generates and returns a random date in the last 30 days.
   *
   * @return a random LocalDate that is maximum 30 days ago from current time.
   */
  private static final LocalDate randomRecentDate() {
    int daysAgo = ThreadLocalRandom.current().nextInt(30); // Random number of days up to 30
    return LocalDate.now().minusDays(daysAgo);
  }

  private static final Map<String, List<String>> CATEGORY_DESCRIPTIONS =
      Map.of(
          "Groceries",
              List.of("Supermarket purchase", "Grocery store", "Bakery shop", "Butcher shop"),
          "Entertainment",
              List.of("Movie tickets", "Concert tickets", "Streaming service subscription"),
          "Utilities",
              List.of("Electric bill payment", "Water bill payment", "Internet bill payment"),
          "Dining", List.of("Restaurant", "Coffee shop", "Fast food"),
          "Transportation", List.of("Gas station", "Public transport ticket", "Taxi fare"),
          "Clothing", List.of("Clothing retail", "Online apparel shop", "Shoe store purchase")
          // ... more categories and descriptions
          );

  private static final List<String> CATEGORIES = List.copyOf(CATEGORY_DESCRIPTIONS.keySet());
  private static final Random RANDOM = new Random();

  /**
   * Creates and saves a random amount of transactions.
   *
   * @param accountNr (Integer) the account number making the fake transaction(s).
   */
  public void storeRandomMockTransactions(Integer accountNr, int amount) {
    for (int i = 0; i < amount; i++) {
      bankService.addTransaction(generateRandomPurchase(accountNr));
    }
  }

  /**
   * Creates a new mock account and adds it to the user.
   *
   * @param email (String): The email of the user the account is being added to
   * @param accountNr (Integer): The account number being created
   * @param isSavingsAcc (boolean): Whether the account is going to be the savings account (if
   *     false; will be checking account)
   * @return the AccountDto that was created.
   */
  @Transactional
  public AccountDto addMockBankAccount(String email, Integer accountNr, boolean isSavingsAcc) {
    // Can change to use a different arg than displayName. ATM I don't know what is stored on the
    // front-end.
    // account nr can also be randomized. to a number like 1 - 1000000.
    User mockUser = findUserByEmail(email);
    UserInfoDto mockUserInfo = userInfoService.getUserInfoByEmail(email);
    AccountDto accountDto = new AccountDto();
    accountDto.setAccountNr(accountNr);
    accountDto.setOwnerId(mockUser.getId());
    String name = mockUserInfo.getFirstName() + " " + mockUserInfo.getLastName();

    if (isSavingsAcc) {
      accountDto.setName(name + "'s savings account");
      mockUser.setSavingsAccountNr(accountNr);
    } else {
      accountDto.setName(accountDto.getName() + "'s checking account");
      mockUser.setCheckingAccountNr(accountNr);
    }

    userRepository.save(mockUser);
    return bankService.createAccount(accountDto);
  }

  private User findUserByEmail(String email) {
    return userRepository
        .findById(userService.getUserByEmail(email).getId())
        .orElseThrow(() -> new NotFoundException("user with email" + email + " not found"));
  }
}
