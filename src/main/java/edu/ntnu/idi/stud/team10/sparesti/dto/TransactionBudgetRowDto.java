package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.Set;

import lombok.*;

/** Data transfer object for TransactionBudgetRowDto */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionBudgetRowDto {
  private Long id;
  private BudgetRowDto budgetRow;
  private Set<TransactionDto> transactions;
}
