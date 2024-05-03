package edu.ntnu.idi.stud.team10.sparesti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Data transfer object for BudgetRow entities. */
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRowDto {
  private Long id;
  private String name;
  private Double usedAmount;
  private Double maxAmount;
  private String category;
  private String emoji;
}
