package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionDtoTest {
  private TransactionDto transactionDto;

  @BeforeEach
  public void setUp() {
    transactionDto = new TransactionDto();
    transactionDto.setId(1L);
    transactionDto.setAmount(100.0);
    transactionDto.setAccountNr(123456L);
    transactionDto.setDescription("Test Description");
    transactionDto.setCategory("Test Category");
    transactionDto.setDate(LocalDate.of(2000, 1, 1));
  }

  @Nested
  class Constructors {
    @Test
    void noArgsConstructor() {
      TransactionDto transactionDto = new TransactionDto();
      assertNotNull(transactionDto);
    }

    @Test
    void allArgsConstructor() {
      TransactionDto transactionDto =
          new TransactionDto(
              1L, 200.0, "Test Description", "Test Category", 123L, LocalDate.of(2000, 1, 1));
      assertEquals(1L, transactionDto.getId());
      assertEquals(200.0, transactionDto.getAmount());
      assertEquals("Test Description", transactionDto.getDescription());
      assertEquals("Test Category", transactionDto.getCategory());
      assertEquals(123L, transactionDto.getAccountNr());
      assertEquals(LocalDate.of(2000, 1, 1), transactionDto.getDate());
    }
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      transactionDto.setId(1L);
      assertEquals(1L, transactionDto.getId());
    }

    @Test
    void getAndSetAmount() {
      transactionDto.setAmount(200.0);
      assertEquals(200.0, transactionDto.getAmount());
    }

    @Test
    void getAndSetAccountNr() {
      transactionDto.setAccountNr(654321L);
      assertEquals(654321L, transactionDto.getAccountNr());
    }

    @Test
    void getAndSetDescription() {
      transactionDto.setDescription("Test Description");
      assertEquals("Test Description", transactionDto.getDescription());
    }

    @Test
    void getAndSetCategory() {
      transactionDto.setCategory("Test Category");
      assertEquals("Test Category", transactionDto.getCategory());
    }

    @Test
    void getAndSetDate() {
      LocalDate testDate = LocalDate.now();
      transactionDto.setDate(testDate);
      assertEquals(testDate, transactionDto.getDate());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private TransactionDto anotherTransactionDto;

    @BeforeEach
    void setUp() {
      anotherTransactionDto = new TransactionDto();
      anotherTransactionDto.setId(1L);
      anotherTransactionDto.setAmount(100.0);
      anotherTransactionDto.setAccountNr(123456L);
      anotherTransactionDto.setDescription("Test Description");
      anotherTransactionDto.setCategory("Test Category");
      anotherTransactionDto.setDate(LocalDate.of(2000, 1, 1));
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(transactionDto, anotherTransactionDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherTransactionDto.setAmount(200.0);
      anotherTransactionDto.setAccountNr(654321L);
      assertNotEquals(transactionDto, anotherTransactionDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(transactionDto.hashCode(), anotherTransactionDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      TransactionDto anotherTransactionDto = new TransactionDto();
      anotherTransactionDto.setAmount(200.0);
      anotherTransactionDto.setAccountNr(654321L);
      assertNotEquals(transactionDto.hashCode(), anotherTransactionDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L})
    void differentIdReturnsFalse(Long id) {
      anotherTransactionDto.setId(id);
      assertNotEquals(transactionDto, anotherTransactionDto);
    }

    @ParameterizedTest
    @ValueSource(doubles = {200.0, 300.0})
    void differentAmountReturnsFalse(Double amount) {
      anotherTransactionDto.setAmount(amount);
      assertNotEquals(transactionDto, anotherTransactionDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Different Description", "Another Description"})
    void differentDescriptionReturnsFalse(String description) {
      anotherTransactionDto.setDescription(description);
      assertNotEquals(transactionDto, anotherTransactionDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Different Category", "Another Category"})
    void differentCategoryReturnsFalse(String category) {
      anotherTransactionDto.setCategory(category);
      assertNotEquals(transactionDto, anotherTransactionDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {654321L, 987654L})
    void differentAccountNrReturnsFalse(Long accountNr) {
      anotherTransactionDto.setAccountNr(accountNr);
      assertNotEquals(transactionDto, anotherTransactionDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"2000-01-02", "2001-01-01"})
    void differentDateReturnsFalse(String dateString) {
      LocalDate date = dateString == null ? null : LocalDate.parse(dateString);
      anotherTransactionDto.setDate(date);
      assertNotEquals(transactionDto, anotherTransactionDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(transactionDto.toString());
  }
}
