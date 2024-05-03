package edu.ntnu.idi.stud.team10.sparesti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.TransactionBudgetRow;

public interface TransactionBudgetRowRepository extends JpaRepository<TransactionBudgetRow, Long> {
  List<TransactionBudgetRow> findByBudgetRow(BudgetRow budgetRow);
}
