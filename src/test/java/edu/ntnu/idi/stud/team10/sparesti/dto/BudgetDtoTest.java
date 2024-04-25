package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;
import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BudgetDtoTest {

  private BudgetDto budgetDto;

  @BeforeEach
  public void setUp() {
    budgetDto = new BudgetDto();
    budgetDto.setId(1L);

    Set<BudgetRow> rows = new HashSet<>();
    rows.add(new BudgetRow("Matvarer", 200, 500, CategoryEnum.GROCERIES));
    rows.add(new BudgetRow("Spill", 200, 500, CategoryEnum.ENTERTAINMENT));
    budgetDto.setRow(rows);

    budgetDto.setExpiryDate(LocalDate.now().plusMonths(1));
  }

  @Test
  public void testBudgetDtoFields() {
    assertEquals(1L, budgetDto.getId());

    Set<BudgetRow> rows = budgetDto.getRow();
    assertNotNull(rows);
    assertEquals(2, rows.size());

    LocalDate expectedExpiryDate = LocalDate.now().plusMonths(1);
    assertEquals(expectedExpiryDate, budgetDto.getExpiryDate());
  }

  @Test
  public void testBudgetDtoConversionToEntity() {
    Budget budget = budgetDto.toEntity();

    assertEquals(1L, budget.getId());

    Set<BudgetRow> rows = budget.getRow();
    assertNotNull(rows);
    assertEquals(2, rows.size());

    LocalDate expectedExpiryDate = LocalDate.now().plusMonths(1);
    assertEquals(expectedExpiryDate, budget.getExpiryDate());
  }
}
