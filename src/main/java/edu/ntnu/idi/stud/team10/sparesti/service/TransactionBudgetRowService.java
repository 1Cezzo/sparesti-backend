package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionBudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionBudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;
import edu.ntnu.idi.stud.team10.sparesti.model.TransactionBudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.repository.TransactionBudgetRowRepository;

/** Service for Transaction Budget Row entities. */
@Service
public class TransactionBudgetRowService {
  private final TransactionBudgetRowRepository transactionBudgetRowRepository;
  private final BankService bankService;
  private final BudgetService budgetService;

  private final TransactionBudgetRowMapper transactionBudgetRowMapper;

  private final BudgetRowMapper budgetRowMapper;
  private final TransactionMapper transactionMapper;

  /**
   * Constructs a TransactionBudgetRowService with the necessary repository, services and mappers.
   *
   * @param transactionBudgetRowRepository Repository for accessing transaction budget row data.
   * @param bankService Service for bank entities.
   * @param budgetService Service for budget entities.
   * @param transactionBudgetRowMapper Mapper for converting between TransactionBudgetRow and
   *     TransactionBudgetRowDto.
   * @param budgetRowMapper Mapper for converting between BudgetRow and BudgetRowDto.
   * @param transactionMapper Mapper for converting between Transaction and TransactionDto.
   */
  @Autowired
  public TransactionBudgetRowService(
      TransactionBudgetRowRepository transactionBudgetRowRepository,
      BankService bankService,
      BudgetService budgetService,
      TransactionBudgetRowMapper transactionBudgetRowMapper,
      BudgetRowMapper budgetRowMapper,
      TransactionMapper transactionMapper) {
    this.transactionBudgetRowRepository = transactionBudgetRowRepository;
    this.bankService = bankService;
    this.budgetService = budgetService;
    this.transactionBudgetRowMapper = transactionBudgetRowMapper;
    this.budgetRowMapper = budgetRowMapper;
    this.transactionMapper = transactionMapper;
  }

  /**
   * Adds a transaction to a transaction budget row.
   *
   * @param transactionBudgetRowDto the DTO representing the transaction budget row to add the
   *     transaction to.
   */
  public void addTransactiontoBudgetRow(TransactionBudgetRowDto transactionBudgetRowDto) {
    transactionBudgetRowRepository.save(
        transactionBudgetRowMapper.toEntity(transactionBudgetRowDto));
  }

  /**
   * Adds the transaction budget row to a transaction.
   *
   * @return a list of all transaction budget rows.
   */
  public void addTransactionBudgetRow(Transaction transaction, BudgetRowDto budgetRowDto) {
    TransactionBudgetRow transactionBudgetRow = new TransactionBudgetRow();
    transactionBudgetRow.setBudgetRow(budgetRowMapper.toEntity(budgetRowDto));
    transactionBudgetRow.getTransactions().add(transaction);
    transactionBudgetRowRepository.save(transactionBudgetRow);
  }

  /**
   * Get all transactions not in a budget row.
   *
   * @return a list of all transaction not in a budget row.
   */
  public Set<Transaction> getTransactionsNotInBudgetRow(Long userId) {
    // Retrieve all transactions
    Set<Transaction> allTransactions = bankService.getTransactionsByUserId(userId);

    // Retrieve all transactions in TransactionBudgetRow
    List<TransactionBudgetRow> transactionBudgetRows = transactionBudgetRowRepository.findAll();

    // Convert TransactionBudgetRow to Transaction
    List<Transaction> transactionsInBudgetRows =
        transactionBudgetRows.stream()
            .flatMap(
                tbr ->
                    tbr
                        .getTransactions()
                        .stream()) // Flatten the Set<Transaction> into a Stream<Transaction>
            .collect(Collectors.toList());

    // Remove transactions found in TransactionBudgetRow from all transactions
    allTransactions.removeAll(transactionsInBudgetRows);

    // Convert the list to a set and return
    return new HashSet<>(allTransactions);
  }
}
