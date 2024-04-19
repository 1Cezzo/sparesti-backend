package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
   * @param accountDto (AccountDto) The account to create
   * @return (ResponseEntity<AccountDto>) The created account
   */
  @PutMapping("/account/create")
  @Operation(summary = "Create a new account")
  public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
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
   * @param accountNr (int) The account number
   * @return (ResponseEntity<AccountDto>) The account details
   */
  @GetMapping("/account/details/{accountNr}")
  @Operation(summary = "Get account details for an account number.")
  public ResponseEntity<AccountDto> getAccountDetails(@PathVariable int accountNr) {
    AccountDto accountDetails = bankService.getAccountDetails(accountNr);
    return ResponseEntity.ok(accountDetails);
  }

  /**
   * Get all accounts for a user.
   *
   * @param userId (Long) The user ID
   * @return (ResponseEntity<Set<AccountDto>>) The set of account details
   */
  @GetMapping("/account/all/{userId}")
  @Operation(summary = "Get all accounts for a user.")
  public ResponseEntity<Set<AccountDto>> getAllAccounts(@PathVariable Long userId) {
    Set<AccountDto> accountDetails = bankService.getUserAccounts(userId);
    return ResponseEntity.ok(accountDetails);
  }
}
