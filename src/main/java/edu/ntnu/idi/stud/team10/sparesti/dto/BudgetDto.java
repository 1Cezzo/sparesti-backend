package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Data transfer object for Budget entities. */
@Data
@AllArgsConstructor
public class BudgetDto {
  private Long id;
  private Set<BudgetRowDto> row;
  private String name;
  private LocalDate expiryDate;
  private LocalDate creationDate;

  /** Constructor for creating a new BudgetDto. */
  public BudgetDto() {
    this.creationDate = LocalDate.now();
  }
}
