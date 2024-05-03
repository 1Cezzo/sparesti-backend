package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.TransactionBudgetRow;

/** Repository for the TransactionBudgetRow entity. */
public interface TransactionBudgetRowRepository extends JpaRepository<TransactionBudgetRow, Long> {

  /**
   * Find all transaction budget rows for a budget row.
   *
   * @param budgetRow The budget row.
   * @return A list of transaction budget rows.
   */
  List<TransactionBudgetRow> findByBudgetRow(BudgetRow budgetRow);
}
