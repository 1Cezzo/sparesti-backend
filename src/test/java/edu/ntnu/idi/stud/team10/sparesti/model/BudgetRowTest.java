package edu.ntnu.idi.stud.team10.sparesti.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BudgetRowTest {

  private BudgetRow budgetRow;
  private final String testName = "Test Name";
  private final double testAmount = 100.0;
  private final CategoryEnum testCategory = CategoryEnum.GROCERIES;

  @BeforeEach
  public void setUp() {
    budgetRow = new BudgetRow(testName, testAmount, testCategory);
  }

  @Test
  public void testConstructor() {
    assertNotNull(budgetRow);
    assertEquals(testName, budgetRow.getName());
    assertEquals(testAmount, budgetRow.getAmount());
    assertEquals(testCategory, budgetRow.getCategory());
  }

  @Test
  public void testUpdateFromDto() {
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    budgetRowDto.setName("Updated Name");
    budgetRowDto.setAmount(200.0);
    budgetRowDto.setCategory(CategoryEnum.TRANSPORTATION);

    budgetRow.updateFromDto(budgetRowDto);

    assertEquals(budgetRowDto.getName(), budgetRow.getName());
    assertEquals(budgetRowDto.getAmount(), budgetRow.getAmount());
    assertEquals(budgetRowDto.getCategory(), budgetRow.getCategory());
  }

  // Add more tests as needed...

}
