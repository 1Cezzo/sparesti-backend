package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BudgetServiceTest {

  @Mock private BudgetRepository budgetRepository;

  @Mock private BankService bankService;

  private BudgetService budgetService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    budgetService = new BudgetService(budgetRepository, bankService);
  }

  @Test
  public void testAddBudget() {
    Budget budget = new Budget();
    when(budgetRepository.save(any())).thenReturn(budget);

    Budget result = budgetService.addBudget(budget);

    assertNotNull(result);
    verify(budgetRepository, times(1)).save(budget);
  }

  @Test
  public void testGetBudgetById() {
    Budget budget = new Budget();
    when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));

    Budget result = budgetService.getBudgetById(1L);

    assertNotNull(result);
    verify(budgetRepository, times(1)).findById(1L);
  }

  @Test
  public void testUpdateBudget() {
    Budget budget = new Budget();
    when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));
    when(budgetRepository.save(any())).thenReturn(budget);

    Budget result = budgetService.updateBudget(budget);

    assertNotNull(result);
    verify(budgetRepository, times(1)).findById(budget.getId());
    verify(budgetRepository, times(1)).save(budget);
  }

  @Test
  public void testDeleteBudget() {
    when(budgetRepository.existsById(any())).thenReturn(true);

    assertDoesNotThrow(() -> budgetService.deleteBudget(1L));
    verify(budgetRepository, times(1)).deleteById(1L);
  }

  @Test
  public void testAddBudgetRowToBudget() {
    Budget budget = new Budget();
    BudgetRow budgetRow = new BudgetRow();
    when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));
    when(budgetRepository.save(any())).thenReturn(budget);

    Budget result = budgetService.addBudgetRowToBudget(1L, budgetRow);

    assertNotNull(result);
    assertTrue(result.getRow().contains(budgetRow));
    verify(budgetRepository, times(1)).findById(1L);
    verify(budgetRepository, times(1)).save(budget);
  }

  @Test
  public void testGetAllBudgetRowsForBudget() {
    Budget budget = new Budget();
    budget.setRow(new HashSet<>()); // Use a HashSet instead of an ArrayList
    when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));

    List<BudgetRow> result = budgetService.getAllBudgetRowsForBudget(1L);

    assertNotNull(result);
    verify(budgetRepository, times(1)).findById(1L);
  }

  @Test
  public void testAddUsedAmountFromTransaction() {
    BudgetRow budgetRow = new BudgetRow();
    budgetRow.setUsedAmount(0);
    Transaction transaction = new Transaction();
    transaction.setAmount(100);

    budgetService.addUsedAmountFromTransaction(budgetRow, transaction);

    assertEquals(100, budgetRow.getUsedAmount());
  }

  @Test
  public void testDeleteBudgetRowFromBudget() {
    Budget budget = new Budget();
    BudgetRow budgetRow = new BudgetRow();
    budgetRow.setId(1L); // Set an id for the BudgetRow
    budget.setRow(new HashSet<>());
    budget.getRow().add(budgetRow);
    when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));
    when(budgetRepository.save(any())).thenReturn(budget);

    budgetService.deleteBudgetRowFromBudget(1L, 1L);

    assertFalse(budget.getRow().contains(budgetRow));
    verify(budgetRepository, times(1)).findById(1L);
    verify(budgetRepository, times(1)).save(budget);
  }
}
