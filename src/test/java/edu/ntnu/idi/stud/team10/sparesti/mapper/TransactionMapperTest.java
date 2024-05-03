package edu.ntnu.idi.stud.team10.sparesti.mapper;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Account;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {

  private TransactionMapper transactionMapper;

  @BeforeEach
  public void setUp() {
    transactionMapper = Mappers.getMapper(TransactionMapper.class);
  }

  @Test
  public void shouldMapTransactionToTransactionDto() {
    // Given
    Account account = new Account(1L, 2L, 123456789L, "Savings Account", 1500.0, new HashSet<>());
    Transaction transaction =
        new Transaction(1L, 200.0, "Dinner", "Food", LocalDate.now(), account);

    // When
    TransactionDto transactionDto = transactionMapper.toDto(transaction);

    // Then
    assertNotNull(transactionDto);
    assertEquals(transaction.getId(), transactionDto.getId());
    assertEquals(transaction.getAmount(), transactionDto.getAmount());
    assertEquals(transaction.getDescription(), transactionDto.getDescription());
    assertEquals(transaction.getCategory(), transactionDto.getCategory());
    assertEquals(transaction.getDate(), transactionDto.getDate());
    assertEquals(
        transaction.getAccount().getAccountNr(),
        transactionDto.getAccountNr(),
        "Account number should be mapped from the account entity");
  }

  @Test
  public void shouldMapTransactionDtoToTransaction() {
    // Given
    TransactionDto transactionDto =
        new TransactionDto(null, 200.0, "Dinner", "Food", 123456789L, LocalDate.now());

    // When
    Transaction transaction = transactionMapper.toEntity(transactionDto);

    // Then
    assertNotNull(transaction);
    assertNull(transaction.getId(), "ID should be null as it's hidden in DTO");
    assertEquals(transactionDto.getAmount(), transaction.getAmount());
    assertEquals(transactionDto.getDescription(), transaction.getDescription());
    assertEquals(transactionDto.getCategory(), transaction.getCategory());
    assertEquals(transactionDto.getDate(), transaction.getDate());
    assertNull(transaction.getAccount(), "Account should be null as it's ignored in mapping");
  }
}
