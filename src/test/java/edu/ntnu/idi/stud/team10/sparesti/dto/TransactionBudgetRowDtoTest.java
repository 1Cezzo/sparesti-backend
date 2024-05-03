package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionBudgetRowDtoTest {
  private TransactionBudgetRowDto transactionBudgetRowDto;

  @BeforeEach
  public void setUp() {
    transactionBudgetRowDto = new TransactionBudgetRowDto();
    transactionBudgetRowDto.setId(1L);

    BudgetRowDto budgetRowDto = new BudgetRowDto();
    transactionBudgetRowDto.setBudgetRow(budgetRowDto);

    Set<TransactionDto> transactions = new HashSet<>();
    TransactionDto transactionDto = new TransactionDto();
    transactions.add(transactionDto);
    transactionBudgetRowDto.setTransactions(transactions);
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      transactionBudgetRowDto.setId(2L);
      assertEquals(2L, transactionBudgetRowDto.getId());
    }

    @Test
    void getAndSetBudgetRow() {
      BudgetRowDto budgetRowDto = new BudgetRowDto();
      transactionBudgetRowDto.setBudgetRow(budgetRowDto);
      assertEquals(budgetRowDto, transactionBudgetRowDto.getBudgetRow());
    }

    @Test
    void getAndSetTransactions() {
      Set<TransactionDto> transactions = new HashSet<>();
      TransactionDto transactionDto = new TransactionDto();
      transactions.add(transactionDto);
      transactionBudgetRowDto.setTransactions(transactions);
      assertEquals(transactions, transactionBudgetRowDto.getTransactions());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private TransactionBudgetRowDto anotherTransactionBudgetRowDto;

    @BeforeEach
    void setUp() {
      anotherTransactionBudgetRowDto = new TransactionBudgetRowDto();
      anotherTransactionBudgetRowDto.setId(1L);

      BudgetRowDto budgetRowDto = new BudgetRowDto();
      anotherTransactionBudgetRowDto.setBudgetRow(budgetRowDto);

      Set<TransactionDto> transactions = new HashSet<>();
      TransactionDto transactionDto = new TransactionDto();
      transactions.add(transactionDto);
      anotherTransactionBudgetRowDto.setTransactions(transactions);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(transactionBudgetRowDto, anotherTransactionBudgetRowDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherTransactionBudgetRowDto.setId(2L);
      assertNotEquals(transactionBudgetRowDto, anotherTransactionBudgetRowDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(transactionBudgetRowDto.hashCode(), anotherTransactionBudgetRowDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherTransactionBudgetRowDto.setId(2L);
      assertNotEquals(
          transactionBudgetRowDto.hashCode(), anotherTransactionBudgetRowDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L, 3L})
    void differentIdReturnsFalse(Long id) {
      anotherTransactionBudgetRowDto.setId(id);
      assertNotEquals(transactionBudgetRowDto, anotherTransactionBudgetRowDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(transactionBudgetRowDto.toString());
  }
}
