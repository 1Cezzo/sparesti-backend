package edu.ntnu.idi.stud.team10.sparesti.model;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.Setter;

/** A row in a budget. It has a name, an amount, and a category. */
@Entity
@Setter
public class BudgetRow {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private double amount;

  @Enumerated(EnumType.STRING)
  private CategoryEnum category;

  @ManyToOne
  @JoinColumn(name = "budget_id")
  private Budget budget;

  /**
   * Constructor for creating a budget row.
   *
   * @param name The name of the budget row.
   * @param amount The amount of the budget row.
   * @param category The category of the budget row.
   */
  public BudgetRow(String name, double amount, CategoryEnum category) {
    this.name = name;
    this.amount = amount;
    this.category = category;
  }

  public BudgetRow() {}

  /**
   * Get the name of the budget row.
   *
   * @return (String) The name of the budget row.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the amount of the budget row.
   *
   * @return (double) The amount of the budget row.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Get the category of the budget row.
   *
   * @return (CategoryEnum) The category of the budget row.
   */
  public CategoryEnum getCategory() {
    return category;
  }

  public Long getId() {
    return id;
  }

  public void updateFromDto(BudgetRowDto budgetRowDto) {
    this.name = budgetRowDto.getName();
    this.amount = budgetRowDto.getAmount();
    this.category = budgetRowDto.getCategory();
  }
}
