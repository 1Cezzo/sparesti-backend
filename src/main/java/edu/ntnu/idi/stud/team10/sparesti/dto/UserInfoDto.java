package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
  private Long id;
  private Long userId;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private OccupationStatus occupationStatus;
  private Integer motivation;
  private Integer income;
  private Set<BudgetingProductDto> budgetingProducts;
  private List<String> budgetingLocations;
}
