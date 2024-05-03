package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionBudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.TransactionRepository;
import edu.ntnu.idi.stud.team10.sparesti.service.BudgetRowService;
import edu.ntnu.idi.stud.team10.sparesti.service.TransactionBudgetRowService;
import edu.ntnu.idi.stud.team10.sparesti.util.TokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/transaction-budget-row")
@Tag(name = "TransactionBudgetRow", description = "Operations related to transaction budget rows")
public class TransactionBudgetRowController {

  private final TransactionBudgetRowService transactionBudgetRowService;
  private final BudgetRowMapper budgetRowMapper;
  private final TransactionMapper transactionMapper;
  private final BudgetRowRepository budgetRowRepository;
  private final TransactionRepository transactionRepository;
  private final BudgetRowService budgetRowService;

  @Autowired
  public TransactionBudgetRowController(
      TransactionBudgetRowService transactionBudgetRowService,
      BudgetRowMapper budgetRowMapper,
      TransactionMapper transactionMapper,
      BudgetRowRepository budgetRowRepository,
      TransactionRepository transactionRepository,
      BudgetRowService budgetRowService) {
    this.transactionBudgetRowService = transactionBudgetRowService;
    this.budgetRowMapper = budgetRowMapper;
    this.transactionMapper = transactionMapper;
    this.budgetRowRepository = budgetRowRepository;
    this.transactionRepository = transactionRepository;
    this.budgetRowService = budgetRowService;
  }

  @PostMapping("/add/{budgetRowId}/{transactionId}")
  @Operation(summary = "Add a transaction to a budget row")
  public ResponseEntity<TransactionBudgetRowDto> addTransactionToBudgetRow(
      @PathVariable Long budgetRowId, @PathVariable Long transactionId) {
    Optional<BudgetRowDto> budgetRowOptional =
        budgetRowRepository.findById(budgetRowId).map(budgetRowMapper::toDto);
    Optional<TransactionDto> transactionOptional =
        transactionRepository.findById(transactionId).map(transactionMapper::toDto);
    if (budgetRowOptional.isPresent() && transactionOptional.isPresent()) {
      TransactionBudgetRowDto transactionBudgetRowDto = new TransactionBudgetRowDto();
      transactionBudgetRowDto.setBudgetRow(budgetRowOptional.get());
      transactionBudgetRowDto.setTransactions(Collections.singleton(transactionOptional.get()));

      // Retrieve the BudgetRow entity from the BudgetRowDto
      BudgetRow budgetRow = budgetRowMapper.toEntity(budgetRowOptional.get());

      // Add the absolute value of the transaction amount to the usedAmount of the BudgetRow
      budgetRow.setUsedAmount(
          budgetRow.getUsedAmount() + Math.abs(transactionOptional.get().getAmount()));

      BudgetRowService.updateBudgetRow(budgetRow.getId(), budgetRowMapper.toDto(budgetRow));

      transactionBudgetRowService.addTransactiontoBudgetRow(transactionBudgetRowDto);
      return ResponseEntity.ok(transactionBudgetRowDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/transactions-not-in-budget-row")
  @Operation(summary = "Get transactions not in a budget row")
  public ResponseEntity<Set<TransactionDto>> getTransactionsNotInBudgetRow(
      @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    Set<TransactionDto> transactionDtos =
        transactionBudgetRowService.getTransactionsNotInBudgetRow(userId).stream()
            .map(transactionMapper::toDto)
            .collect(Collectors.toSet());
    return ResponseEntity.ok(transactionDtos);
  }
}
