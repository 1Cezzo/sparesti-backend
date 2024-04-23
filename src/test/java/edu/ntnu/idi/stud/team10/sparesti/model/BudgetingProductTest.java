package edu.ntnu.idi.stud.team10.sparesti.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BudgetingProductTest {

  private BudgetingProduct budgetingProduct;
  private final Long testId = 1L;
  private final String testName = "Test Product";
  private final TimeInterval testFrequency = TimeInterval.MONTHLY;
  private final Integer testAmount = 100;
  private UserInfo testUserInfo;

  @BeforeEach
  public void setUp() {
    budgetingProduct = new BudgetingProduct();
    budgetingProduct.setId(testId);
    budgetingProduct.setName(testName);
    budgetingProduct.setFrequency(testFrequency);
    budgetingProduct.setAmount(testAmount);
    testUserInfo = new UserInfo();
    budgetingProduct.setUserInfo(testUserInfo);
  }

  @Test
  public void testBudgetingProductAttributes() {
    assertNotNull(budgetingProduct);
    assertEquals(testId, budgetingProduct.getId());
    assertEquals(testName, budgetingProduct.getName());
    assertEquals(testFrequency, budgetingProduct.getFrequency());
    assertEquals(testAmount, budgetingProduct.getAmount());
    assertEquals(testUserInfo, budgetingProduct.getUserInfo());
  }

  @Test
  public void testEqualsAndHashCode() {
    BudgetingProduct product1 =
        new BudgetingProduct(testId, testName, testFrequency, testAmount, testUserInfo);
    BudgetingProduct product2 =
        new BudgetingProduct(testId, testName, testFrequency, testAmount, testUserInfo);
    BudgetingProduct product3 =
        new BudgetingProduct(2L, "Another Product", TimeInterval.WEEKLY, 50, new UserInfo());

    assertEquals(product1, product2);
    assertNotEquals(product1, product3);
    assertEquals(product1.hashCode(), product2.hashCode());
    assertNotEquals(product1.hashCode(), product3.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "BudgetingProduct(id=1, name=Test Product, frequency=MONTHLY, amount=100, userInfo="
            + testUserInfo.toString()
            + ")";
    assertEquals(expectedToString, budgetingProduct.toString());
  }

  @Test
  public void testConstructor() {
    assertEquals(testId, budgetingProduct.getId());
    assertEquals(testName, budgetingProduct.getName());
    assertEquals(testFrequency, budgetingProduct.getFrequency());
    assertEquals(testAmount, budgetingProduct.getAmount());
    assertEquals(testUserInfo, budgetingProduct.getUserInfo());
  }
}
