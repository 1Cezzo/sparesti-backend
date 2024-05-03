package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.TransactionBudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.TransactionBudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Budget entities that are connected to a user. */
@Service
public class UserBudgetService {
  private final UserRepository userRepository;

  private final BudgetRepository budgetRepository;

  private final BudgetRowRepository budgetRowRepository;

  private final UserMapper userMapper;

  private final BudgetMapper budgetMapper;

  private final BudgetRowMapper budgetRowMapper;
  private TransactionBudgetRowRepository transactionBudgetRowRepository;

  @Autowired
  public UserBudgetService(
      UserRepository userRepository,
      BudgetRepository budgetRepository,
      BudgetRowRepository budgetRowRepository,
      UserMapper userMapper,
      BudgetMapper budgetMapper,
      BudgetRowMapper budgetRowMapper,
      TransactionBudgetRowRepository transactionBudgetRowRepository) {
    this.userRepository = userRepository;
    this.budgetRepository = budgetRepository;
    this.budgetRowRepository = budgetRowRepository;
    this.userMapper = userMapper;
    this.budgetMapper = budgetMapper;
    this.budgetRowMapper = budgetRowMapper;
    this.transactionBudgetRowRepository = transactionBudgetRowRepository;
  }

  /**
   * Adds a budget to a user.
   *
   * @param userId the user id to add the budget for.
   * @param budgetDto the budget to add.
   * @return the updated user.
   */
  public BudgetDto addBudgetToUser(Long userId, BudgetDto budgetDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget = budgetMapper.toEntity(budgetDto);
    budget.setUser(user);
    if (budget.getRow() != null) {
      budget.getRow().forEach(row -> row.setBudget(budget));
    }
    return budgetMapper.toDto(budgetRepository.save(budget));
  }

  /**
   * Gets all budgets for a user.
   *
   * @param userId the user id to get budgets for.
   * @return a list of budget DTOs.
   */
  public List<BudgetDto> getAllBudgetsForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    return budgetRepository.findByUser(user).stream().map(budgetMapper::toDto).toList();
  }

  /**
   * Gets a budget for a user.
   *
   * @param userId the ID of the user.
   * @param budgetId the ID of the budget.
   * @return the budget DTO.
   */
  public BudgetDto getBudgetForUser(Long userId, Long budgetId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    return budgetMapper.toDto(budget);
  }

  /**
   * Deletes a budget from a user.
   *
   * @param userId the user id to delete the budget from.
   * @param budgetId the budget id to delete.
   */
  public void deleteBudgetFromUser(Long userId, Long budgetId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    Set<BudgetRow> budgetRows = budget.getRow();

    for (BudgetRow budgetRow : budgetRows) {
      // Retrieve and delete all TransactionBudgetRow entities that reference this BudgetRow
      List<TransactionBudgetRow> transactionBudgetRows =
          transactionBudgetRowRepository.findByBudgetRow(budgetRow);
      transactionBudgetRowRepository.deleteAll(transactionBudgetRows);
      budgetRowRepository.delete(budgetRow);
    }

    budgetRepository.delete(budget);
  }

  /**
   * Adds a budget row to a user's budget.
   *
   * @param userId the user id to add the budget row for.
   * @param budgetId the budget id to add the budget row for.
   * @param budgetRowDto the budget row to add.
   * @return the updated budget.
   */
  public BudgetDto addBudgetRowToUserBudget(Long userId, Long budgetId, BudgetRowDto budgetRowDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow = budgetRowMapper.toEntity(budgetRowDto);
    budgetRow.setBudget(budget);
    budget.getRow().add(budgetRow);
    budgetRepository.save(budget);

    return budgetMapper.toDto(budget);
  }

  /**
   * Deletes a budget row from a user's budget.
   *
   * @param userId the user id to delete the budget row for.
   * @param budgetId the budget id to delete the budget row for.
   * @param budgetRowId the budget row id to delete.
   */
  public void deleteBudgetRowFromUserBudget(Long userId, Long budgetId, Long budgetRowId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow =
        budgetRowRepository
            .findById(budgetRowId)
            .orElseThrow(
                () -> new NotFoundException("BudgetRow with ID " + budgetRowId + " not found"));

    budget.getRow().remove(budgetRow);
    budgetRepository.save(budget);
  }

  /**
   * Edits a budget row in a user's budget.
   *
   * @param userId the user id to edit the budget row for.
   * @param budgetId the budget id to edit the budget row for.
   * @param budgetRowId the budget row id to edit.
   * @param budgetRowDto the new data for the budget row.
   * @return the updated budget row.
   */
  public BudgetRowDto editBudgetRowInUserBudget(
      Long userId, Long budgetId, Long budgetRowId, BudgetRowDto budgetRowDto) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    Budget budget =
        budgetRepository
            .findById(budgetId)
            .orElseThrow(() -> new NotFoundException("Budget with ID " + budgetId + " not found"));

    if (!budget.getUser().equals(user)) {
      throw new IllegalArgumentException("The budget does not belong to the user");
    }

    BudgetRow budgetRow =
        budgetRowRepository
            .findById(budgetRowId)
            .orElseThrow(
                () -> new NotFoundException("BudgetRow with ID " + budgetRowId + " not found"));

    // Update the budget row with the new data
    budgetRowMapper.updateFromDto(budgetRowDto, budgetRow);
    budgetRowRepository.save(budgetRow);

    return budgetRowMapper.toDto(budgetRow);
  }

  /**
   * Retrieves the newest Budget entity for a user, will return an empty Optional if no budgets are
   *
   * @param userId The ID of the user
   */
  public Optional<BudgetDto> getNewestBudget(Long userId) {
    List<Budget> budgets = budgetRepository.findByUserId(userId);
    if (budgets.isEmpty()) {
      return Optional.empty();
    }
    Budget newestBudget = budgets.get(0);
    for (Budget budget : budgets) {
      if (budget.getCreationDate().isAfter(newestBudget.getCreationDate())) {
        newestBudget = budget;
      }
    }
    if (newestBudget.getExpiryDate().isBefore(LocalDate.now())) {
      return Optional.empty();
    }
    return Optional.of(budgetMapper.toDto(newestBudget));
  }
}
