package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoDtoTest {

  private UserInfoDto userInfo;

  @BeforeEach
  public void setUp() {
    userInfo = new UserInfoDto();
    userInfo.setId(1L);
    userInfo.setUserId(2L);
    userInfo.setDisplayName("JohnDoe");
    userInfo.setFirstName("John");
    userInfo.setLastName("Doe");
    userInfo.setDateOfBirth(LocalDate.of(1990, 1, 1));
    userInfo.setOccupationStatus(OccupationStatus.EMPLOYED);
    userInfo.setMotivation(5);
    userInfo.setIncome(1000);
    userInfo.setBudgetingProducts(new HashSet<>());
    userInfo.setBudgetingLocations(List.of("Location1", "Location2"));
  }

  @Test
  void testId() {
    Long id = 1L;
    userInfo.setId(id);
    assertEquals(id, userInfo.getId());
  }

  @Test
  void testUserId() {
    Long userId = 2L;
    userInfo.setUserId(userId);
    assertEquals(userId, userInfo.getUserId());
  }

  @Test
  void testFirstName() {
    String firstName = "John";
    userInfo.setFirstName(firstName);
    assertEquals(firstName, userInfo.getFirstName());
  }

  @Test
  void testLastName() {
    String lastName = "Doe";
    userInfo.setLastName(lastName);
    assertEquals(lastName, userInfo.getLastName());
  }

  @Test
  void testDateOfBirth() {
    LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
    userInfo.setDateOfBirth(dateOfBirth);
    assertEquals(dateOfBirth, userInfo.getDateOfBirth());
  }

  @Test
  void testOccupationStatus() {
    OccupationStatus occupationStatus = OccupationStatus.EMPLOYED;
    userInfo.setOccupationStatus(occupationStatus);
    assertEquals(occupationStatus, userInfo.getOccupationStatus());
  }

  @Test
  void testMotivation() {
    Integer motivation = 5;
    userInfo.setMotivation(motivation);
    assertEquals(motivation, userInfo.getMotivation());
  }

  @Test
  void testIncome() {
    Integer income = 1000;
    userInfo.setIncome(income);
    assertEquals(income, userInfo.getIncome());
  }

  @Test
  void testBudgetingProducts() {
    Set<BudgetingProductDto> budgetingProducts = new HashSet<>();
    // add some budgeting products
    userInfo.setBudgetingProducts(budgetingProducts);
    assertEquals(budgetingProducts, userInfo.getBudgetingProducts());
  }

  @Test
  void testBudgetingLocations() {
    List<String> budgetingLocations = List.of("Location1", "Location2");
    userInfo.setBudgetingLocations(budgetingLocations);
    assertEquals(budgetingLocations, userInfo.getBudgetingLocations());
  }

  @Test
  void testConstructor() {
    Long id = 1L;
    Long userId = 1L;
    String displayName = "JohnDoe";
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
            displayName,
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
    assertEquals(displayName, userInfo.getDisplayName());
    assertEquals(firstName, userInfo.getFirstName());
    assertEquals(lastName, userInfo.getLastName());
    assertEquals(dateOfBirth, userInfo.getDateOfBirth());
    assertEquals(occupationStatus, userInfo.getOccupationStatus());
    assertEquals(motivation, userInfo.getMotivation());
    assertEquals(income, userInfo.getIncome());
    assertEquals(budgetingProducts, userInfo.getBudgetingProducts());
    assertEquals(budgetingLocations, userInfo.getBudgetingLocations());
  }

  @Nested
  class EqualsAndHashcode {
    private UserInfoDto anotherUserInfoDto;

    @BeforeEach
    void setUp() {
      anotherUserInfoDto = new UserInfoDto();
      anotherUserInfoDto.setId(1L);
      anotherUserInfoDto.setUserId(2L);
      anotherUserInfoDto.setDisplayName("JohnDoe");
      anotherUserInfoDto.setFirstName("John");
      anotherUserInfoDto.setLastName("Doe");
      anotherUserInfoDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
      anotherUserInfoDto.setOccupationStatus(OccupationStatus.EMPLOYED);
      anotherUserInfoDto.setMotivation(5);
      anotherUserInfoDto.setIncome(1000);
      anotherUserInfoDto.setBudgetingProducts(new HashSet<>());
      anotherUserInfoDto.setBudgetingLocations(List.of("Location1", "Location2"));
    }

    @Test
    void objectsWithSameDataAreEqual() {
      assertEquals(userInfo, anotherUserInfoDto);
    }

    @Test
    void objectsWithSameDataHaveSameHashcode() {
      assertEquals(userInfo.hashCode(), anotherUserInfoDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L, 3L})
    void differentIdReturnsFalse(Long id) {
      anotherUserInfoDto.setId(id);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {3L, 4L})
    void differentUserIdReturnsFalse(Long userId) {
      anotherUserInfoDto.setUserId(userId);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"JaneDoe", "JohnSmith"})
    void differentDisplayNameReturnsFalse(String displayName) {
      anotherUserInfoDto.setDisplayName(displayName);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Jane", "Johnathan"})
    void differentFirstNameReturnsFalse(String firstName) {
      anotherUserInfoDto.setFirstName(firstName);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Smith", "Johnson"})
    void differentLastNameReturnsFalse(String lastName) {
      anotherUserInfoDto.setLastName(lastName);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"1991-01-01", "1989-12-31"})
    void differentDateOfBirthReturnsFalse(String dateString) {
      LocalDate dateOfBirth = dateString == null ? null : LocalDate.parse(dateString);
      anotherUserInfoDto.setDateOfBirth(dateOfBirth);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @EnumSource(
        value = OccupationStatus.class,
        mode = EnumSource.Mode.EXCLUDE,
        names = {"EMPLOYED"})
    void differentOccupationStatusReturnsFalse(OccupationStatus occupationStatus) {
      anotherUserInfoDto.setOccupationStatus(occupationStatus);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {4, 6})
    void differentMotivationReturnsFalse(Integer motivation) {
      anotherUserInfoDto.setMotivation(motivation);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {999, 1001})
    void differentIncomeReturnsFalse(Integer income) {
      anotherUserInfoDto.setIncome(income);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @Test
    void differentBudgetingProductsReturnsFalse() {
      Set<BudgetingProductDto> budgetingProducts = new HashSet<>();
      budgetingProducts.add(new BudgetingProductDto());
      anotherUserInfoDto.setBudgetingProducts(budgetingProducts);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }

    @Test
    void differentBudgetingLocationsReturnsFalse() {
      List<String> budgetingLocations = List.of("Location3", "Location4");
      anotherUserInfoDto.setBudgetingLocations(budgetingLocations);
      assertNotEquals(userInfo, anotherUserInfoDto);
    }
  }

  @Test
  void testToString() {
    assertNotNull(userInfo.toString());
  }
}
