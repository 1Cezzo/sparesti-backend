package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetRowDtoTest {

  private BudgetRowDto budgetRowDto1;
  private BudgetRowDto budgetRowDto2;

  @BeforeEach
  public void setUp() {
    budgetRowDto1 = new BudgetRowDto();
    budgetRowDto1.setId(1L);
    budgetRowDto1.setName("Test Category");
    budgetRowDto1.setMaxAmount(500.0);
    budgetRowDto1.setCategory("Groceries");

    budgetRowDto2 = new BudgetRowDto();
    budgetRowDto2.setId(1L);
    budgetRowDto2.setName("Test Category");
    budgetRowDto2.setMaxAmount(500.0);
    budgetRowDto2.setCategory("Groceries");
  }

  @Test
  public void testBudgetRowDtoFields() {
    assertEquals(1L, budgetRowDto1.getId());
    assertEquals("Test Category", budgetRowDto1.getName());
    assertEquals(500.0, budgetRowDto1.getMaxAmount());
    assertEquals("Groceries", budgetRowDto1.getCategory());
  }

  @Test
  public void testBudgetRowDtoConversionToEntity() {
    BudgetRow budgetRow = BudgetRowMapper.INSTANCE.toEntity(budgetRowDto1);

    assertEquals(1L, budgetRow.getId());
    assertEquals("Test Category", budgetRow.getName());
    assertEquals(500.0, budgetRow.getMaxAmount());
    assertEquals("Groceries", budgetRow.getCategory());
  }

  @Test
  public void testEquals() {
    assertEquals(budgetRowDto1, budgetRowDto2);
  }

  @Test
  public void testNotEquals() {
    budgetRowDto2.setMaxAmount(1000.0);
    assertNotEquals(budgetRowDto1, budgetRowDto2);
  }

  @Test
  public void testHashCode() {
    assertEquals(budgetRowDto1.hashCode(), budgetRowDto2.hashCode());
  }
}
