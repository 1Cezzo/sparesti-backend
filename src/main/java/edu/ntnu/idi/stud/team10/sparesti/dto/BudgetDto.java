package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetDto {
  private Long id;
  private Set<BudgetRow> row = new HashSet<>();
  private LocalDate expiryDate;

  public BudgetDto(Budget budget) {
    this.id = budget.getId();
    this.row = budget.getRow();
    this.expiryDate = LocalDate.now().plusMonths(1);
  }

  public BudgetDto() {
    this.expiryDate = LocalDate.now().plusMonths(1);
  }

  public Budget toEntity() {
    Budget budget = new Budget();
    budget.setId(id);
    budget.setRow(row);
    budget.setExpiryDate(expiryDate);
    return budget;
  }
}
