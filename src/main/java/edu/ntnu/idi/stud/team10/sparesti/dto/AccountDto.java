package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dto to represent an account entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
  @Hidden private Long id;
  private Long ownerId;
  private int accountNr;
  private String name;
  private double balance;

  @Hidden private Set<TransactionDto> transactions = new HashSet<>();
}
