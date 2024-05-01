package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static edu.ntnu.idi.stud.team10.sparesti.config.AuthorizationServerConfig.USER_ID_CLAIM;

/** Controller to mock interactions with a bank. */
@RestController
@RequestMapping("/api/bank")
@Tag(name = "Bank", description = "Operations related to the bank mock")
public class BankController {
  private final BankService bankService;

  @Autowired
  public BankController(BankService bankService) {
    this.bankService = bankService;
  }

  /**
   * Create a new account.
   *
   * @param accountDto (AccountDto) The account to create.
   * @param token (Jwt) The JWT token.
   * @return (ResponseEntity&lt;AccountDto&gt;) The created account
   */
  @PutMapping("/account/create")
  @Operation(summary = "Create a new account")
  public ResponseEntity<AccountDto> createAccount(
      @RequestBody AccountDto accountDto, @AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    accountDto.setOwnerId(userId);
    AccountDto createdAccount = bankService.createAccount(accountDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
  }

  /**
   * Add a transaction to an account.
   *
   * @param transaction (TransactionDto) The transaction to add
   * @return (ResponseEntity<Void>) The response entity
   */
  @PostMapping("/account/transactions")
  @Operation(summary = "Add a transaction to an account.")
  public ResponseEntity<Void> addTransaction(@RequestBody TransactionDto transaction) {
    bankService.addTransaction(transaction);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * Get account details for an account number.
   *
   * @param accountNr (Long) The account number
   * @param token (Jwt) The JWT token
   * @return (ResponseEntity<AccountDto>) The account details
   */
  @GetMapping("/account/details/{accountNr}")
  @Operation(summary = "Get account details for an account number.")
  public ResponseEntity<AccountDto> getAccountDetails(
      @PathVariable Long accountNr, @AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    AccountDto accountDetails = bankService.getAccountDetails(accountNr, userId);
    return ResponseEntity.ok(accountDetails);
  }

  /**
   * Get all accounts for a user.
   *
   * @param token (Jwt) The JWT token
   * @return (ResponseEntity<Set<AccountDto>>) The set of account details
   */
  @GetMapping("/account/all")
  @Operation(summary = "Get all accounts for a user.")
  public ResponseEntity<Set<AccountDto>> getAllAccounts(@AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    Set<AccountDto> accountDetails = bankService.getUserAccounts(userId);
    return ResponseEntity.ok(accountDetails);
  }

  /**
   * Endpoint for transferring money between two accounts.
   *
   * @param fromAccountNr the account number where money is coming from
   * @param toAccountNr the account number receiving money
   * @param amount the amount of money being transferred, in NOK.
   * @return ResponseEntity&lt;?&gt; stating that the transfer was successful.
   */
  @PutMapping("/account/transfer")
  @Operation(summary = "Transfer money from one account to another")
  public ResponseEntity<String> transferMoney(
      @RequestParam Long fromAccountNr,
      @RequestParam Long toAccountNr,
      @RequestParam double amount,
      @AuthenticationPrincipal Jwt token) {
    Long ownerId = token.getClaim(USER_ID_CLAIM);
    bankService.transferMoney(fromAccountNr, toAccountNr, amount, ownerId);
    return ResponseEntity.ok().body("Transfer successful");
  }

  /**
   * Gets a list of all transactions by a singular account number
   *
   * @param accountNr (Long) The accountNr
   * @return (ResponseEntity&lt;Set&lt;TransactionDto&gt; &gt;) Set of all transactions by the
   *     account.
   */
  @GetMapping("/transactions/{accountNr}")
  @Operation(summary = "Get all transactions by an account number")
  public ResponseEntity<Set<TransactionDto>> getAllTransactionsByAccountNr(
      @PathVariable Long accountNr, @AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    return ResponseEntity.ok().body(bankService.getTransactionsByAccountNr(accountNr, userId));
  }

  /**
   * Get the last 30 days of transactions from an account, using its number.
   *
   * @param accountNr (Long) the accounts unique number in the bank.
   * @param token (Jwt) the JWT token
   * @return (ResponseEntity&lt;Set&lt;TransactionDto&gt;&gt;): a set including the last 30 days of
   *     transactions for the account.
   */
  @GetMapping("/transactions/recent/{accountNr}")
  @Operation(summary = "Get last 30 days of transactions from an account number")
  public ResponseEntity<Set<TransactionDto>> getRecentTransactions(
      @PathVariable Long accountNr, @AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    return ResponseEntity.ok()
        .body(bankService.getRecentTransactionsByAccountNr(accountNr, userId));
  }

  /**
   * Gets an account's total spendings in various categories from the last 30 days.
   *
   * @param accountNr (Long) the accounts unique number in the bank.
   * @param token (Jwt) the JWT token.
   * @return (Map&lt;String Double&gt;)
   */
  @GetMapping("/transactions/recent/categories/{accountNr}")
  @Operation(
      summary = "Get the sum spent in each category in the last 30 days for an account number")
  public ResponseEntity<Map<String, Double>> getRecentCategorySpendings(
      @PathVariable Long accountNr, @AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim(USER_ID_CLAIM);
    return ResponseEntity.ok().body(bankService.getSpendingsInCategories(accountNr, userId));
  }
}
