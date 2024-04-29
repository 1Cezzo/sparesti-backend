package edu.ntnu.idi.stud.team10.sparesti.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BudgetRowTest {

  private BudgetRow budgetRow;
  private final String testName = "Test Name";
  private final double testMaxAmount = 200.0;
  private final double testUsedAmount = 100.0;
  private final String testCategory = "Groceries";
  private final String testEmoji = "🍕";

  @BeforeEach
  public void setUp() {
    budgetRow = new BudgetRow(testName, testUsedAmount, testMaxAmount, testCategory, testEmoji);
  }

  @Test
  public void testConstructor() {
    assertNotNull(budgetRow);
    assertEquals(testName, budgetRow.getName());
    assertEquals(testUsedAmount, budgetRow.getUsedAmount());
    assertEquals(testCategory, budgetRow.getCategory());
  }

  @Test
  public void testUpdateFromDto() {
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    budgetRowDto.setName("Updated Name");
    budgetRowDto.setMaxAmount(250.0);
    budgetRowDto.setCategory("Transportation");

    budgetRow.updateFromDto(budgetRowDto);

    assertEquals(budgetRowDto.getName(), budgetRow.getName());
    assertEquals(budgetRowDto.getMaxAmount(), budgetRow.getMaxAmount());
    assertEquals(budgetRowDto.getCategory(), budgetRow.getCategory());
  }

  // Add more tests as needed...

}
