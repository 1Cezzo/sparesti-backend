package edu.ntnu.idi.stud.team10.sparesti.model;

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
  private Double usedAmount;
  private Double maxAmount;
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

  /** Constructor for creating a budget row. */
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

  /**
   * Get the id of the budget row.
   *
   * @return (Long) The id of the budget row.
   */
  public Long getId() {
    return id;
  }

  /**
   * Get the emoji of the budget row.
   *
   * @return (String) The emoji of the budget row.
   */
  public String getEmoji() {
    return emoji;
  }
}
