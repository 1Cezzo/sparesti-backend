package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private Long id;
    private Set<BudgetRow> row = new HashSet<>();

    public BudgetDto(Budget budget) {
        this.id = budget.getId();
        this.row = budget.getRow();
    }
}
