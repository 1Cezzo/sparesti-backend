package edu.ntnu.idi.stud.team10.sparesti.model;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
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
  private double usedAmount;
  private double maxAmount;
  private String category;
  private String emoji;

  @ManyToOne
  @JoinColumn(name = "budget_id")
  private Budget budget;

  /**
   * Constructor for creating a budget row.
   *
   * @param name The name of the budget row.
   * @param usedAmount The amount of the budget row.
   * @param maxAmount The maximum amount of the budget row.
   * @param category The category of the budget row.
   */
  public BudgetRow(
      String name, double usedAmount, double maxAmount, String category, String emoji) {
    this.name = name;
    this.usedAmount = usedAmount;
    this.maxAmount = maxAmount;
    this.category = category;
    this.emoji = emoji;
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
   * Get the used amount of the budget row.
   *
   * @return (double) The amount of the budget row.
   */
  public double getUsedAmount() {
    return usedAmount;
  }

  /**
   * Get the maximum amount of the budget row.
   *
   * @return (double) The maximum amount of the budget row.
   */
  public double getMaxAmount() {
    return maxAmount;
  }

  /**
   * Get the category of the budget row.
   *
   * @return (CategoryEnum) The category of the budget row.
   */
  public String getCategory() {
    return category;
  }

  public Long getId() {
    return id;
  }

  public String getEmoji() {
    return emoji;
  }

  public void updateFromDto(BudgetRowDto budgetRowDto) {
    this.name = budgetRowDto.getName();
    this.usedAmount = budgetRowDto.getUsedAmount();
    this.maxAmount = budgetRowDto.getMaxAmount();
    this.category = budgetRowDto.getCategory();
    this.emoji = budgetRowDto.getEmoji();
  }
}
