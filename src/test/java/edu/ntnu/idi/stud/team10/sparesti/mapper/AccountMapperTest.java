package edu.ntnu.idi.stud.team10.sparesti.mapper;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {

    private AccountMapper accountMapper;

    @BeforeEach
    public void setUp() {
        // Initialize the mapper
        accountMapper = Mappers.getMapper(AccountMapper.class);
    }

    @Test
    public void shouldMapAccountToAccountDto() {
        // Given
        Account account = new Account(1L, 2L, 123456789L, "Savings Account", 1500.0, new HashSet<>());

        // When
        AccountDto accountDto = accountMapper.toDto(account);

        // Then
        assertNotNull(accountDto);
        assertEquals(account.getId(), accountDto.getId());
        assertEquals(account.getOwnerId(), accountDto.getOwnerId());
        assertEquals(account.getAccountNr(), accountDto.getAccountNr());
        assertEquals(account.getName(), accountDto.getName());
        assertEquals(account.getBalance(), accountDto.getBalance());
        // Assuming transaction mapping is correctly handled
        assertNotNull(accountDto.getTransactions());
    }

    @Test
    public void shouldMapAccountDtoToAccount() {
        // Given
        AccountDto accountDto = new AccountDto(1L, 2L, 123456789L, "Savings Account", 1500.0, new HashSet<>());

        // When
        Account account = accountMapper.toEntity(accountDto);

        // Then
        assertNotNull(account);
        assertEquals(accountDto.getId(), account.getId());
        assertEquals(accountDto.getOwnerId(), account.getOwnerId());
        assertEquals(accountDto.getAccountNr(), account.getAccountNr());
        assertEquals(accountDto.getName(), account.getName());
        assertEquals(accountDto.getBalance(), account.getBalance());
        // Assuming transaction mapping is correctly handled
        assertNotNull(account.getTransactions());
    }
}
