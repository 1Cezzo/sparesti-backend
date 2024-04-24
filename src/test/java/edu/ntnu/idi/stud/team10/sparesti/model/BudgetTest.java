package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BudgetTest {

  private Budget budget;
  private User user;

  @BeforeEach
  public void setUp() {
    budget = new Budget();
    budget.setId(1L);
    budget.setRow(new HashSet<>());
    budget.setExpiryDate(LocalDate.of(2024, 12, 31));
    user = new User();
    user.setId(1L);
    user.setPassword("testPassword");
    user.setEmail("test@example.com");
    user.setProfilePictureUrl("https://example.com/profile.jpg");
    budget.setUser(user);
  }

  @Test
  public void testBudgetFields() {
    assertEquals(1L, budget.getId());
    assertNotNull(budget.getRow());
    assertTrue(budget.getRow().isEmpty());
    assertEquals(LocalDate.of(2024, 12, 31), budget.getExpiryDate());
    assertEquals(user, budget.getUser());
  }

  @Test
  public void testBudgetRowAssociation() {
    BudgetRow budgetRow = new BudgetRow();
    Set<BudgetRow> rows = new HashSet<>();
    budgetRow.setId(1L);
    budgetRow.setName("Test Budget Row");
    budgetRow.setMaxAmount(500.0);
    budgetRow.setCategory(CategoryEnum.GROCERIES);
    rows.add(budgetRow);
    budget.setRow(rows);

    assertEquals(1, budget.getRow().size());
    assertTrue(budget.getRow().contains(budgetRow));
  }

  @Test
  public void testToString() {
    User user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");

    Budget budget = new Budget();
    budget.setId(1L);
    budget.setExpiryDate(LocalDate.of(2024, 12, 31));
    budget.setUser(user);

    String expectedString =
        "Budget(id=1, row=[], expiryDate=2024-12-31, user=User{id=1, email='test@example.com'})";
    assertEquals(expectedString, budget.toString());
  }
}
