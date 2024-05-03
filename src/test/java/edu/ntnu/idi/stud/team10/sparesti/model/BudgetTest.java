package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BudgetTest {
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

  @Nested
  class Constructors {
    @Test
    void noArgsConstructor() {
      Budget newBudget = new Budget();
      assertNotNull(newBudget);
    }

    @Test
    void allArgsConstructor() {
      Budget newBudget =
          new Budget(
              1L,
              new HashSet<BudgetRow>(),
              "Name",
              LocalDate.of(3000, 1, 1),
              LocalDate.of(2024, 1, 1),
              new User());
      assertNotNull(newBudget);
    }
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      budget.setId(2L);
      assertEquals(2L, budget.getId());
    }

    @Test
    void getAndSetRow() {
      HashSet<BudgetRow> rows = new HashSet<>();
      budget.setRow(rows);
      assertEquals(rows, budget.getRow());
    }

    @Test
    void getAndSetExpiryDate() {
      LocalDate newExpiryDate = LocalDate.of(2025, 12, 31);
      budget.setExpiryDate(newExpiryDate);
      assertEquals(newExpiryDate, budget.getExpiryDate());
    }

    @Test
    void getAndSetUser() {
      User newUser = new User();
      newUser.setId(2L);
      newUser.setPassword("newPassword");
      newUser.setEmail("new@example.com");
      newUser.setProfilePictureUrl("https://example2.com/profile.jpg");
      budget.setUser(newUser);
      assertEquals(newUser, budget.getUser());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private Budget anotherBudget;

    @BeforeEach
    void setUp() {
      anotherBudget = new Budget();
      anotherBudget.setId(1L);
      anotherBudget.setRow(new HashSet<>());
      anotherBudget.setExpiryDate(LocalDate.of(2024, 12, 31));
      User anotherUser = new User();
      anotherUser.setId(1L);
      anotherUser.setPassword("testPassword");
      anotherUser.setEmail("test@example.com");
      anotherUser.setProfilePictureUrl("https://example.com/profile.jpg");
      anotherBudget.setUser(anotherUser);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(budget, anotherBudget);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherBudget.setId(2L);
      assertNotEquals(budget, anotherBudget);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(budget.hashCode(), anotherBudget.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherBudget.setId(2L);
      assertNotEquals(budget.hashCode(), anotherBudget.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(budget.toString());
  }
}
