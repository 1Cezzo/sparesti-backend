package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;
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
    budgetRowDto1.setAmount(500.0);
    budgetRowDto1.setCategory(CategoryEnum.GROCERIES);

    budgetRowDto2 = new BudgetRowDto();
    budgetRowDto2.setId(1L);
    budgetRowDto2.setName("Test Category");
    budgetRowDto2.setAmount(500.0);
    budgetRowDto2.setCategory(CategoryEnum.GROCERIES);
  }

  @Test
  public void testBudgetRowDtoFields() {
    assertEquals(1L, budgetRowDto1.getId());
    assertEquals("Test Category", budgetRowDto1.getName());
    assertEquals(500.0, budgetRowDto1.getAmount());
    assertEquals(CategoryEnum.GROCERIES, budgetRowDto1.getCategory());
  }

  @Test
  public void testBudgetRowDtoConversionToEntity() {
    BudgetRow budgetRow = budgetRowDto1.toEntity();

    assertEquals(1L, budgetRow.getId());
    assertEquals("Test Category", budgetRow.getName());
    assertEquals(500.0, budgetRow.getAmount());
    assertEquals(CategoryEnum.GROCERIES, budgetRow.getCategory());
  }

  @Test
  public void testEquals() {
    assertTrue(budgetRowDto1.equals(budgetRowDto2));
  }

  @Test
  public void testNotEquals() {
    budgetRowDto2.setAmount(1000.0);
    assertFalse(budgetRowDto1.equals(budgetRowDto2));
  }

  @Test
  public void testHashCode() {
    assertEquals(budgetRowDto1.hashCode(), budgetRowDto2.hashCode());
  }
}
