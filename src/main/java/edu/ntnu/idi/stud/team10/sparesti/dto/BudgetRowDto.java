package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRowDto {

    private Long id;

    private String name;
    private double amount;
    private CategoryEnum category;

    public BudgetRowDto(BudgetRow budgetRow) {
        this.id = budgetRow.getId();
        this.name = budgetRow.getName();
        this.amount = budgetRow.getAmount();
        this.category = budgetRow.getCategory();
    }
}
