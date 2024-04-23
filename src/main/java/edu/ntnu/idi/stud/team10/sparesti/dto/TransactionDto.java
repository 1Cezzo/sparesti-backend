package edu.ntnu.idi.stud.team10.sparesti.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/** Dto to represent a transaction entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
  @Hidden private Long id;
  private double amount;
  private String description;
  private String category;
  private int accountNr;
  private LocalDate date;
}
