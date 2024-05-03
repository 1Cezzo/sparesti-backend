package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
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

class BudgetDtoTest {
  private BudgetDto budgetDto;

  @BeforeEach
  public void setUp() {
    budgetDto = new BudgetDto();
    budgetDto.setId(1L);

    Set<BudgetRowDto> rows = new HashSet<>();
    BudgetRowDto row1 = new BudgetRowDto();
    rows.add(row1);

    budgetDto.setRow(rows);
    budgetDto.setName("Test budget");
    budgetDto.setExpiryDate(LocalDate.now().plusMonths(1));
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      budgetDto.setId(2L);
      assertEquals(2L, budgetDto.getId());
    }

    @Test
    void getAndSetRow() {
      Set<BudgetRowDto> rows = new HashSet<>();
      BudgetRowDto row2 = new BudgetRowDto();
      rows.add(row2);
      budgetDto.setRow(rows);
      assertEquals(rows, budgetDto.getRow());
    }

    @Test
    void getAndSetName() {
      budgetDto.setName("Test budget 2");
      assertEquals("Test budget 2", budgetDto.getName());
    }

    @Test
    void getAndSetExpiryDate() {
      LocalDate testDate = LocalDate.now().plusMonths(2);
      budgetDto.setExpiryDate(testDate);
      assertEquals(testDate, budgetDto.getExpiryDate());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private BudgetDto anotherBudgetDto;

    @BeforeEach
    void setUp() {
      anotherBudgetDto = new BudgetDto();
      anotherBudgetDto.setId(1L);

      Set<BudgetRowDto> rows = new HashSet<>();
      BudgetRowDto row1 = new BudgetRowDto();
      rows.add(row1);

      anotherBudgetDto.setRow(rows);
      anotherBudgetDto.setName("Test budget");
      anotherBudgetDto.setExpiryDate(LocalDate.now().plusMonths(1));
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(budgetDto, anotherBudgetDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherBudgetDto.setName("Test budget 2");
      assertNotEquals(budgetDto, anotherBudgetDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(budgetDto.hashCode(), anotherBudgetDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherBudgetDto.setName("Test budget 2");
      assertNotEquals(budgetDto.hashCode(), anotherBudgetDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L})
    void differentIdReturnsFalse(Long id) {
      anotherBudgetDto.setId(id);
      assertNotEquals(budgetDto, anotherBudgetDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Test budget 2", "Test budget 3"})
    void differentNameReturnsFalse(String name) {
      anotherBudgetDto.setName(name);
      assertNotEquals(budgetDto, anotherBudgetDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"2022-01-02", "2023-01-01"})
    void differentExpiryDateReturnsFalse(String dateString) {
      LocalDate date = dateString == null ? null : LocalDate.parse(dateString);
      anotherBudgetDto.setExpiryDate(date);
      assertNotEquals(budgetDto, anotherBudgetDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(budgetDto.toString());
  }
}
