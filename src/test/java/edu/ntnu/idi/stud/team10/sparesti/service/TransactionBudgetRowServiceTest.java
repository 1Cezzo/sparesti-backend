package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionBudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionBudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;
import edu.ntnu.idi.stud.team10.sparesti.model.TransactionBudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.repository.TransactionBudgetRowRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionBudgetRowServiceTest {

  @Mock private TransactionBudgetRowRepository transactionBudgetRowRepository;

  @Mock private BankService bankService;

  @Mock private BudgetService budgetService;

  @Mock private BudgetRowMapper budgetRowMapper;

  @Mock private TransactionBudgetRowMapper transactionBudgetRowMapper;

  @Mock private TransactionMapper transactionMapper;

  private TransactionBudgetRowService transactionBudgetRowService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    transactionBudgetRowService =
        new TransactionBudgetRowService(
            transactionBudgetRowRepository,
            bankService,
            budgetService,
            transactionBudgetRowMapper,
            budgetRowMapper,
            transactionMapper);
  }

  @Test
  void testAddTransactiontoBudgetRow() {
    // Arrange
    TransactionBudgetRowDto transactionBudgetRowDto = new TransactionBudgetRowDto();
    TransactionBudgetRow transactionBudgetRow = new TransactionBudgetRow();
    when(transactionBudgetRowMapper.toEntity(transactionBudgetRowDto))
        .thenReturn(transactionBudgetRow);

    // Act
    transactionBudgetRowService.addTransactiontoBudgetRow(transactionBudgetRowDto);

    // Assert
    verify(transactionBudgetRowRepository, times(1)).save(transactionBudgetRow);
  }

  @Test
  void addTransactionBudgetRow_ShouldSaveTransactionBudgetRowWithTransaction() {
    Transaction transaction = new Transaction();
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    transactionBudgetRowService.addTransactionBudgetRow(transaction, budgetRowDto);
    verify(transactionBudgetRowRepository, times(1)).save(any(TransactionBudgetRow.class));
  }

  @Test
  void getTransactionsNotInBudgetRow_ShouldReturnTransactionsNotInBudgetRow() {
    Long userId = 1L;
    Set<Transaction> allTransactions = new HashSet<>();
    allTransactions.add(new Transaction());
    allTransactions.add(new Transaction());

    when(bankService.getTransactionsByUserId(userId)).thenReturn(allTransactions);
    when(transactionBudgetRowRepository.findAll()).thenReturn(List.of(new TransactionBudgetRow()));

    Set<Transaction> result = transactionBudgetRowService.getTransactionsNotInBudgetRow(userId);

    assertEquals(allTransactions, result);
  }

  @Test
  void testGetTransactionsNotInBudgetRow() {
    // Arrange
    Long userId = 1L;
    Set<Transaction> allTransactions = new HashSet<>();
    Set<Transaction> transactionsInBudgetRows = new HashSet<>();

    // Mocking behavior of bankService and transactionBudgetRowRepository
    when(bankService.getTransactionsByUserId(userId)).thenReturn(allTransactions);
    when(transactionBudgetRowRepository.findAll()).thenReturn(List.of());

    // Act
    Set<Transaction> result = transactionBudgetRowService.getTransactionsNotInBudgetRow(userId);

    // Assert
    assertEquals(allTransactions, result);
  }

  @Test
  void testAddTransactionBudgetRow() {
    // Arrange
    Transaction transaction = new Transaction();
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    TransactionBudgetRow transactionBudgetRow = new TransactionBudgetRow();

    // Mocking behavior of budgetRowMapper
    when(budgetRowMapper.toEntity(budgetRowDto)).thenReturn(new BudgetRow());

    // Act
    transactionBudgetRowService.addTransactionBudgetRow(transaction, budgetRowDto);

    // Assert
    verify(transactionBudgetRowRepository, times(1)).save(any(TransactionBudgetRow.class));
  }
}
