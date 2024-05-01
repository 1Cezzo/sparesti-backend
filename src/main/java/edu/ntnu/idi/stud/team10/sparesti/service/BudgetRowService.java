package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;

/** Service for Budget Row entities. */
@Service
public class BudgetRowService {
  private final BudgetRowRepository budgetRowRepository;
  private final BudgetRowMapper budgetRowMapper;

  @Autowired
  public BudgetRowService(
      BudgetRowRepository budgetRowRepository, BudgetRowMapper budgetRowMapper) {
    this.budgetRowRepository = budgetRowRepository;
    this.budgetRowMapper = budgetRowMapper;
  }

  /**
   * Creates a new BudgetRow entity.
   *
   * @param budgetRowDto the DTO representing the budget row to create
   * @return the created budget row
   */
  public BudgetRow createBudgetRow(BudgetRowDto budgetRowDto) {
    BudgetRow budgetRow = budgetRowMapper.toEntity(budgetRowDto);
    return budgetRowRepository.save(budgetRow);
  }

  /** Retrieves all SavingsGoal entities. */
  public List<BudgetRow> getAllBudgetRows() {
    return budgetRowRepository.findAll();
  }

  /**
   * Retrieves a SavingsGoal entity by its ID.
   *
   * @param id the ID of the savings goal
   * @return the savings goal if it exists, or an empty Optional otherwise
   */
  public Optional<BudgetRow> getBudgetRowById(Long id) {
    return budgetRowRepository.findById(id);
  }

  /**
   * Updates a BudgetRow entity.
   *
   * @param id the ID of the budget row
   * @param budgetRowDto the DTO representing the budget row to update
   * @return the updated budget row
   */
  public BudgetRow updateBudgetRow(Long id, BudgetRowDto budgetRowDto) {
    Optional<BudgetRow> optionalBudgetRow = budgetRowRepository.findById(id);
    if (optionalBudgetRow.isPresent()) {
      BudgetRow budgetRow = optionalBudgetRow.get();
      budgetRow.setName(budgetRowDto.getName());
      budgetRow.setUsedAmount(budgetRowDto.getUsedAmount());
      budgetRow.setMaxAmount(budgetRowDto.getMaxAmount());
      budgetRow.setCategory(budgetRowDto.getCategory());
      return budgetRowRepository.save(budgetRow);
    } else {
      throw new IllegalArgumentException("Budget row with ID " + id + " not found");
    }
  }

  /**
   * Deletes a BudgetRow entity by its ID.
   *
   * @param id the ID of the budget row
   */
  public void deleteBudgetRowById(Long id) {
    try {
      budgetRowRepository.deleteById(id);
    } catch (Exception e) {
      throw new IllegalArgumentException("Budget row with ID " + id + " not found");
    }
  }
}
