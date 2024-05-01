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
import edu.ntnu.idi.stud.team10.sparesti.util.ConflictException;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for mock data creation */
@Service
public class MockDataService {
  private final BankService bankService;
  private final UserInfoService userInfoService;

  @Autowired
  public MockDataService(BankService bankService, UserInfoService userInfoService) {
    this.bankService = bankService;
    this.userInfoService = userInfoService;
  }

  /**
   * Creates and returns a random transaction to an account. Intended for creation of mock data for
   * bank. The date will always be a random day in the last 30 days.
   *
   * @param accountNr number of the account (who the transaction is coming from)
   * @return a randomly generated transaction
   */
  public TransactionDto generateRandomPurchase(Long accountNr) {
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
  private static LocalDate randomRecentDate() {
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
          List.of(
              "Betaling av strømregning",
              "Betaling av vannregning",
              "Betaling av internettregning"),
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
   * @param accountNr (Long) the account number making the fake transaction(s).
   */
  public void storeRandomMockTransactions(Long accountNr, int amount) {
    for (int i = 0; i < amount; i++) {
      bankService.addTransaction(generateRandomPurchase(accountNr));
    }
  }

  /**
   * Creates and initializes a mock bank account.
   *
   * @param account (AccountDto) a Dto representing the account to mock.
   * @throws NotFoundException if the user does not exist.
   * @throws ConflictException if the account number is already in use.
   * @throws IllegalArgumentException if the account number is invalid.
   */
  @Transactional
  public void createMockBankAccount(AccountDto account) {
    UserInfoDto mockUserInfo = userInfoService.getUserInfoByUserId(account.getOwnerId());
    String name = mockUserInfo.getFirstName() + " " + mockUserInfo.getLastName();
    account.setName(name + "'s account");
    bankService.createAccount(account);
    initFakeAccount(account.getAccountNr());
  }

  /**
   * Generates a random bank account number.
   *
   * @return (int) a random number between 100000 and 999999
   */
  private int generateUniqueAccountNumber() {
    return ThreadLocalRandom.current().nextInt(100000, 999999);
  }

  private void initFakeAccount(Long accountNr) {
    TransactionDto transactionDto = new TransactionDto();
    transactionDto.setAccountNr(accountNr);
    transactionDto.setDate(LocalDate.now().minusDays(31));
    transactionDto.setAmount(20000);
    transactionDto.setCategory("Donation");
    transactionDto.setDescription("<3 TO MY FAVORITE CHILD, A BIG MONEY DONO");
    bankService.addTransaction(transactionDto);

    storeRandomMockTransactions(accountNr, 60);
    // according to a singular google search:
    // The average bank transaction amount is 59.5 per person per month.
  }
}
