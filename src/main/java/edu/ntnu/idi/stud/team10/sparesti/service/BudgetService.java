package edu.ntnu.idi.stud.team10.sparesti.service;

import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    /**
     * Creates a new Budget entity.
     *
     * @param budget the budget to create
     * @return the created budget
     */

    public Budget addBudget(Budget budget) {
        if (budget == null) {
            throw new IllegalArgumentException("Budget cannot be null");
        }
        return budgetRepository.save(budget);
    }

    /**
     * Retrieves all Budget entities.
     *
     * @param id the ID of the budget
     * @return the budget if it exists, or an empty Optional otherwise
     */
    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Budget with id " + id + " not found"));
    }

    /**
     * Updates a Budget entity.
     *
     * @param budget the budget to update
     * @return the updated budget
     */

    public Budget updateBudget(Budget budget) {
        if (budget == null) {
            throw new IllegalArgumentException("Budget cannot be null");
        }
        Budget budgetToUpdate = getBudgetById(budget.getId());

        // Update all non-null fields included in the request.
        // Add your fields here

        return budgetRepository.save(budgetToUpdate);
    }

    /**
     * Deletes a Budget entity.
     *
     * @param id the ID of the budget
     */

    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new InvalidIdException("Budget with id " + id + " not found");
        }
        budgetRepository.deleteById(id);
    }

    /**
     * Adds a BudgetRow to a Budget.
     *
     * @param budgetId The ID of the budget to add the row to
     * @param budgetRow  The budget row to add
     * @return the updated budget
     */

    public Budget addBudgetRowToBudget(Long budgetId, BudgetRow budgetRow) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new InvalidIdException("Budget with ID " + budgetId + " not found"));

        budgetRow.setBudget(budget);
        budget.getRow().add(budgetRow);
        budgetRepository.save(budget);
        return budget;
    }

    /**
     * Retrieves all BudgetRow entities for a Budget.
     *
     * @param budgetId The ID of the budget
     * @return the budget rows if they exist, or an empty list otherwise
     */

    public List<BudgetRow> getAllBudgetRowsForBudget(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new InvalidIdException("Budget with ID " + budgetId + " not found"));

        return new ArrayList<>(budget.getRow());
    }

    /**
     * Retrieves a BudgetRow entity by its ID.
     *
     * @param budgetId The ID of the budget
     * @param budgetRowId The ID of the budget row
     */

    public void deleteBudgetRowFromBudget(Long budgetId, Long budgetRowId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new InvalidIdException("Budget with ID " + budgetId + " not found"));

        BudgetRow budgetRow = budget.getRow().stream()
                .filter(row -> row.getId().equals(budgetRowId))
                .findFirst()
                .orElseThrow(() -> new InvalidIdException("BudgetRow with ID " + budgetRowId + " not found"));

        budget.getRow().remove(budgetRow);
        budgetRepository.save(budget);
    }
}