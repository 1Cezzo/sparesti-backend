package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetingProductDto {
  private Long id;
  private String name;
  private TimeInterval frequency;
  private Integer amount;
  private Long userInfoId;
}
