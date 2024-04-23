package edu.ntnu.idi.stud.team10.sparesti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.idi.stud.team10.sparesti.service.MockDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/** Controller for creating mock data. Not for use in production. */
@RestController
@RequestMapping("/api/mock")
@Tag(name = "Mock Data", description = "Operations relating to all mock data creation")
public class MockDataController {

  private MockDataService mockDataService;

  @Autowired
  public MockDataController(MockDataService mockDataService) {
    this.mockDataService = mockDataService;
  }

  @PutMapping("/accounts/generate")
  @Operation(summary = "create and connect an account to a user")
  public ResponseEntity<?> addMockAccountToUser(
      @RequestParam String email,
      @RequestParam boolean isSavingsAccount,
      @RequestParam Integer accountNr) {
    mockDataService.addMockBankAccount(email, accountNr, isSavingsAccount);
    return ResponseEntity.ok().body("Account added successfully");
  }

  /**
   * Endpoint to generate a specified number of random transactions for a given account.
   *
   * @param accountNr The account number for which to generate transactions.
   * @param count The number of transactions to generate.
   * @return A ResponseEntity indicating success or failure.
   */
  @PutMapping("/transactions/generate")
  @Operation(summary = "Generate an amount of random mock purchases for an account")
  public ResponseEntity<?> generateMockTransactions(
      @RequestParam Integer accountNr, @RequestParam(defaultValue = "10") int count) {

    mockDataService.storeRandomMockTransactions(accountNr, count);
    return ResponseEntity.ok(
        "Successfully generated " + count + " random transactions for account " + accountNr);
  }
}
