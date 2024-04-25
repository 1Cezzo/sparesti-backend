package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
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

  @Autowired
  public MockDataService(
      UserRepository userRepository,
      BankService bankService,
      UserService userService,
      UserInfoService userInfoService) {
    this.userRepository = userRepository;
    this.bankService = bankService;
    this.userService = userService;
    this.userInfoService = userInfoService;
  }

  /**
   * Creates and returns a random transaction to an account. Intended for creation of mock data for
   * bank.
   * The date will always be a random day in the last 30 days.
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
                  "Dagligvarer",
                  List.of("Supermarkedkjøp", "Dagligvarebutikk", "Bakeri", "Slakter"),
                  "Underholdning",
                  List.of("Kinobilletter", "Konsertbilletter", "Abonnement på strømmetjeneste"),
                  "Nyttetjenester",
                  List.of("Betaling av strømregning", "Betaling av vannregning", "Betaling av internettregning"),
                  "Spisesteder",
                  List.of("Restaurant", "Kafé", "Hurtigmat"),
                  "Transport",
                  List.of("Bensinstasjon", "Offentlig transportbillett", "Taxi"),
                  "Klær",
                  List.of("Klærforretning", "Nettbutikk for klær", "Skobutikk")
                  // ... flere kategorier og beskrivelser
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
   * @param isSavingsAcc (boolean): Whether the account is going to be the savings account (if
   *     false; will be checking account)
   * @return the AccountDto that was created.
   */
  @Transactional
  public AccountDto addMockBankAccount(String email, boolean isSavingsAcc) {
    // Can change to use a different arg than displayName. ATM I don't know what is stored on the
    User mockUser = findUserByEmail(email);
    UserInfoDto mockUserInfo = userInfoService.getUserInfoByEmail(email);
    int accountNr = generateUniqueAccountNumber();
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

  /**
   * Generates a random bank account number.
   *
   * @return (int) a random number between 100000 and 999999
   */
  private int generateUniqueAccountNumber() {
    return ThreadLocalRandom.current().nextInt(100000, 999999);
  }

  /**
   * Finds and returns a user of email.
   *
   * @param email (String) the email/displayname of the user.
   * @return (User) the User, if found.
   * @throws NotFoundException if the user is not found
   */
  private User findUserByEmail(String email) {
    return userRepository
        .findById(userService.getUserByEmail(email).getId())
        .orElseThrow(() -> new NotFoundException("user with email" + email + " not found"));
  }

  /**
   * Assigns a mock savings and checkings account.
   *
   * @param name (String) user's display-name or first + last name
   * @param checkingAccountNr (Integer) number for the checking account
   * @param savingsAccountNr (Integer) number for the savings account
   * @param userId (Long) id of the user.
   */
  @Transactional
  public void assignQuestionnaireMockAccounts(String name, Integer checkingAccountNr, Integer savingsAccountNr, Long userId) {
    AccountDto checkingAccountDto = new AccountDto();
    checkingAccountDto.setAccountNr(checkingAccountNr);
    checkingAccountDto.setOwnerId(userId);
    checkingAccountDto.setName(name + "'s checking account");
    bankService.createAccount(checkingAccountDto);

    AccountDto savingsAccountDto = new AccountDto();
    savingsAccountDto.setAccountNr(savingsAccountNr);
    savingsAccountDto.setOwnerId(userId);
    savingsAccountDto.setName(name + "'s savings account");
    bankService.createAccount(savingsAccountDto);

    userService.setUserAccount(checkingAccountDto, false);
    userService.setUserAccount(savingsAccountDto, true);

    //Add a bunch of money and transactions.
    initFakeAccount(checkingAccountDto);
  }

  private void initFakeAccount(AccountDto accountDto) {
    TransactionDto transactionDto = new TransactionDto();
    transactionDto.setAccountNr(accountDto.getAccountNr());
    transactionDto.setDate(LocalDate.now().minusDays(31));
    transactionDto.setAmount(20000);
    transactionDto.setCategory("Donation");
    transactionDto.setDescription("<3 TO MY FAVORITE CHILD, A BIG MONEY DONO");
    bankService.addTransaction(transactionDto);

    storeRandomMockTransactions(accountDto.getAccountNr(), 60);
    // according to a singular google search:
    // The average bank transaction amount is 59.5 per person per month.
  }
}
