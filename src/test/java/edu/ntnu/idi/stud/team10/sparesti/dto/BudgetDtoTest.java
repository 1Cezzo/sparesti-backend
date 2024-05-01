package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BudgetDtoTest {

  private BudgetDto budgetDto;

  @BeforeEach
  public void setUp() {
    budgetDto = new BudgetDto();
    budgetDto.setId(1L);

    Set<BudgetRow> rows = new HashSet<>();
    rows.add(new BudgetRow("Matvarer", 200, 500, "Groceries", "🍕"));
    rows.add(new BudgetRow("Spill", 200, 500, "Entertainment", "🎮"));
    budgetDto.setRow(
        rows.stream().map(BudgetRowMapper.INSTANCE::toDto).collect(Collectors.toSet()));

    budgetDto.setExpiryDate(LocalDate.now().plusMonths(1));
  }

  @Test
  void testBudgetDtoFields() {
    assertEquals(1L, budgetDto.getId());

    Set<BudgetRow> rows =
        budgetDto.getRow().stream()
            .map(BudgetRowMapper.INSTANCE::toEntity)
            .collect(Collectors.toSet());
    assertNotNull(rows);
    assertEquals(2, rows.size());

    LocalDate expectedExpiryDate = LocalDate.now().plusMonths(1);
    assertEquals(expectedExpiryDate, budgetDto.getExpiryDate());
  }
}
