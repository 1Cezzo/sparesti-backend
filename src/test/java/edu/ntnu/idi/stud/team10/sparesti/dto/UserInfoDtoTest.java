package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;

import static org.junit.jupiter.api.Assertions.*;

public class UserInfoDtoTest {

  private UserInfoDto userInfo;

  @BeforeEach
  public void setUp() {
    userInfo = new UserInfoDto();
  }

  @Test
  public void testId() {
    Long id = 1L;
    userInfo.setId(id);
    assertEquals(id, userInfo.getId());
  }

  @Test
  public void testUserId() {
    Long userId = 2L;
    userInfo.setUserId(userId);
    assertEquals(userId, userInfo.getUserId());
  }

  @Test
  public void testFirstName() {
    String firstName = "John";
    userInfo.setFirstName(firstName);
    assertEquals(firstName, userInfo.getFirstName());
  }

  @Test
  public void testLastName() {
    String lastName = "Doe";
    userInfo.setLastName(lastName);
    assertEquals(lastName, userInfo.getLastName());
  }

  @Test
  public void testDateOfBirth() {
    LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
    userInfo.setDateOfBirth(dateOfBirth);
    assertEquals(dateOfBirth, userInfo.getDateOfBirth());
  }

  @Test
  public void testOccupationStatus() {
    OccupationStatus occupationStatus = OccupationStatus.EMPLOYED;
    userInfo.setOccupationStatus(occupationStatus);
    assertEquals(occupationStatus, userInfo.getOccupationStatus());
  }

  @Test
  public void testMotivation() {
    Integer motivation = 5;
    userInfo.setMotivation(motivation);
    assertEquals(motivation, userInfo.getMotivation());
  }

  @Test
  public void testIncome() {
    Integer income = 1000;
    userInfo.setIncome(income);
    assertEquals(income, userInfo.getIncome());
  }

  @Test
  public void testBudgetingProducts() {
    Set<BudgetingProductDto> budgetingProducts = new HashSet<>();
    // add some budgeting products
    userInfo.setBudgetingProducts(budgetingProducts);
    assertEquals(budgetingProducts, userInfo.getBudgetingProducts());
  }

  @Test
  public void testBudgetingLocations() {
    List<String> budgetingLocations = List.of("Location1", "Location2");
    userInfo.setBudgetingLocations(budgetingLocations);
    assertEquals(budgetingLocations, userInfo.getBudgetingLocations());
  }

  @Test
  public void testEquals() {
    UserInfoDto userInfo1 =
        new UserInfoDto(
            1L,
            1L,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            OccupationStatus.EMPLOYED,
            5,
            1000,
            new HashSet<>(),
            List.of("Location1", "Location2"));
    UserInfoDto userInfo2 =
        new UserInfoDto(
            1L,
            1L,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            OccupationStatus.EMPLOYED,
            5,
            1000,
            new HashSet<>(),
            List.of("Location1", "Location2"));
    UserInfoDto userInfo3 =
        new UserInfoDto(
            2L,
            1L,
            "Jane",
            "Doe",
            LocalDate.of(1990, 1, 1),
            OccupationStatus.EMPLOYED,
            5,
            1000,
            new HashSet<>(),
            List.of("Location1", "Location2"));

    assertEquals(userInfo1, userInfo2);
    assertNotEquals(userInfo1, userInfo3);
  }

  @Test
  public void testHashCode() {
    UserInfoDto userInfo1 =
        new UserInfoDto(
            1L,
            1L,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            OccupationStatus.EMPLOYED,
            5,
            1000,
            new HashSet<>(),
            List.of("Location1", "Location2"));
    UserInfoDto userInfo2 =
        new UserInfoDto(
            1L,
            1L,
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            OccupationStatus.EMPLOYED,
            5,
            1000,
            new HashSet<>(),
            List.of("Location1", "Location2"));

    assertEquals(userInfo1.hashCode(), userInfo2.hashCode());
  }

  @Test
  public void testConstructor() {
    Long id = 1L;
    Long userId = 1L;
    String firstName = "John";
    String lastName = "Doe";
    LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
    OccupationStatus occupationStatus = OccupationStatus.EMPLOYED;
    Integer motivation = 5;
    Integer income = 1000;
    Set<BudgetingProductDto> budgetingProducts = new HashSet<>();
    List<String> budgetingLocations = List.of("Location1", "Location2");

    UserInfoDto userInfo =
        new UserInfoDto(
            id,
            userId,
            firstName,
            lastName,
            dateOfBirth,
            occupationStatus,
            motivation,
            income,
            budgetingProducts,
            budgetingLocations);

    assertEquals(id, userInfo.getId());
    assertEquals(userId, userInfo.getUserId());
    assertEquals(firstName, userInfo.getFirstName());
    assertEquals(lastName, userInfo.getLastName());
    assertEquals(dateOfBirth, userInfo.getDateOfBirth());
    assertEquals(occupationStatus, userInfo.getOccupationStatus());
    assertEquals(motivation, userInfo.getMotivation());
    assertEquals(income, userInfo.getIncome());
    assertEquals(budgetingProducts, userInfo.getBudgetingProducts());
    assertEquals(budgetingLocations, userInfo.getBudgetingLocations());
  }
}
