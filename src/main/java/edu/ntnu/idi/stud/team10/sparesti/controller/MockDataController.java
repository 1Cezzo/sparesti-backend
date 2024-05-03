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

  private final MockDataService mockDataService;

  /**
   * Constructor for MockDataController.
   *
   * @param mockDataService (MockDataService) The mock data service
   */
  @Autowired
  public MockDataController(MockDataService mockDataService) {
    this.mockDataService = mockDataService;
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
      @RequestParam Long accountNr, @RequestParam(defaultValue = "10") int count) {
    mockDataService.storeRandomMockTransactions(accountNr, count);
    return ResponseEntity.ok(
        "Successfully generated " + count + " random transactions for account " + accountNr);
  }
}
