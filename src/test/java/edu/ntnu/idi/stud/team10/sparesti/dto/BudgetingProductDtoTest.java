package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BudgetingProductDtoTest {

  @Test
  public void testConstructorAndGetters() {
    Long id = 1L;
    String name = "Budgeting Product 1";
    TimeInterval frequency = TimeInterval.MONTHLY;
    Integer amount = 100;
    Double unitPrice = 20.0;
    Long userInfoId = 1L;

    BudgetingProductDto budgetingProduct =
        new BudgetingProductDto(id, name, frequency, amount, unitPrice, userInfoId);

    assertNotNull(budgetingProduct);
    assertEquals(id, budgetingProduct.getId());
    assertEquals(name, budgetingProduct.getName());
    assertEquals(frequency, budgetingProduct.getFrequency());
    assertEquals(amount, budgetingProduct.getAmount());
    assertEquals(unitPrice, budgetingProduct.getUnitPrice());
    assertEquals(userInfoId, budgetingProduct.getUserInfoId());
  }

  @Test
  public void testEqualsAndHashCode() {
    BudgetingProductDto product1 =
        new BudgetingProductDto(1L, "Budgeting Product 1", TimeInterval.MONTHLY, 100, 20.0, 1L);
    BudgetingProductDto product2 =
        new BudgetingProductDto(1L, "Budgeting Product 1", TimeInterval.MONTHLY, 100, 20.0, 1L);
    BudgetingProductDto product3 =
        new BudgetingProductDto(2L, "Budgeting Product 2", TimeInterval.WEEKLY, 50, 20.0, 2L);

    assertEquals(product1, product2);
    assertNotEquals(product1, product3);
    assertEquals(product1.hashCode(), product2.hashCode());
    assertNotEquals(product1.hashCode(), product3.hashCode());
  }

  @Test
  public void testSetterAndGetters() {
    BudgetingProductDto budgetingProduct = new BudgetingProductDto();
    budgetingProduct.setId(1L);
    budgetingProduct.setName("Budgeting Product 1");
    budgetingProduct.setFrequency(TimeInterval.MONTHLY);
    budgetingProduct.setAmount(100);
    budgetingProduct.setUnitPrice(20.0);
    budgetingProduct.setUserInfoId(1L);

    assertEquals(1L, budgetingProduct.getId());
    assertEquals("Budgeting Product 1", budgetingProduct.getName());
    assertEquals(TimeInterval.MONTHLY, budgetingProduct.getFrequency());
    assertEquals(100, budgetingProduct.getAmount());
    assertEquals(20.0, budgetingProduct.getUnitPrice());
    assertEquals(1L, budgetingProduct.getUserInfoId());
  }

  @Test
  public void testConstructorWithParameters() {
    Long id = 1L;
    String name = "Budgeting Product 1";
    TimeInterval frequency = TimeInterval.MONTHLY;
    Integer amount = 100;
    Double unitPrice = 20.0;
    Long userInfoId = 1L;

    BudgetingProductDto budgetingProduct =
        new BudgetingProductDto(id, name, frequency, amount, unitPrice, userInfoId);

    assertEquals(id, budgetingProduct.getId());
    assertEquals(name, budgetingProduct.getName());
    assertEquals(frequency, budgetingProduct.getFrequency());
    assertEquals(amount, budgetingProduct.getAmount());
    assertEquals(unitPrice, budgetingProduct.getUnitPrice());
    assertEquals(userInfoId, budgetingProduct.getUserInfoId());
  }

  @Test
  public void testToString() {
    Long id = 1L;
    String name = "Budgeting Product 1";
    TimeInterval frequency = TimeInterval.MONTHLY;
    Integer amount = 100;
    Double unitPrice = 20.0;
    Long userInfoId = 1L;

    BudgetingProductDto budgetingProduct =
        new BudgetingProductDto(id, name, frequency, amount, unitPrice, userInfoId);

    String expectedToString =
        "BudgetingProductDto(id=1, name=Budgeting Product 1, frequency=MONTHLY, amount=100, unitPrice=20.0, "
            + "userInfoId=1)";
    assertEquals(expectedToString, budgetingProduct.toString());
  }
}
