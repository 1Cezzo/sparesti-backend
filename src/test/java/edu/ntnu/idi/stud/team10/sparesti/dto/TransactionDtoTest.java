package edu.ntnu.idi.stud.team10.sparesti.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionDtoTest {

    private TransactionDto transactionDto;

    @BeforeEach
    public void setUp() {
        transactionDto = new TransactionDto();
        transactionDto.setAmount(100.0);
        transactionDto.setAccountNr(123456);
    }

    @Test
    public void testTransactionDtoFields() {
        assertEquals(100.0, transactionDto.getAmount());
        assertEquals(123456, transactionDto.getAccountNr());
    }
}
