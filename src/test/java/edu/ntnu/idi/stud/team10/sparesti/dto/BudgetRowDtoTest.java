package edu.ntnu.idi.stud.team10.sparesti.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;

public class BudgetRowDtoTest {

    private BudgetRowDto budgetRowDto;

    @BeforeEach
    public void setUp() {
        budgetRowDto = new BudgetRowDto();
        budgetRowDto.setId(1L);
        budgetRowDto.setName("Test Category");
        budgetRowDto.setAmount(500.0);
        budgetRowDto.setCategory(CategoryEnum.GROCERIES);
    }

    @Test
    public void testBudgetRowDtoFields() {
        assertEquals(1L, budgetRowDto.getId());
        assertEquals("Test Category", budgetRowDto.getName());
        assertEquals(500.0, budgetRowDto.getAmount());
        assertEquals(CategoryEnum.GROCERIES, budgetRowDto.getCategory());
    }

    @Test
    public void testBudgetRowDtoConversionToEntity() {
        BudgetRow budgetRow = budgetRowDto.toEntity();

        assertEquals(1L, budgetRow.getId());
        assertEquals("Test Category", budgetRow.getName());
        assertEquals(500.0, budgetRow.getAmount());
        assertEquals(CategoryEnum.GROCERIES, budgetRow.getCategory());
    }
}
