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

@Service
public class TransactionBudgetRowService {
  private final TransactionBudgetRowRepository transactionBudgetRowRepository;
  private final BankService bankService;
  private final BudgetService budgetService;

  private final TransactionBudgetRowMapper transactionBudgetRowMapper;

  private final BudgetRowMapper budgetRowMapper;
  private final TransactionMapper transactionMapper;

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

  public void addTransactiontoBudgetRow(TransactionBudgetRowDto transactionBudgetRowDto) {
    transactionBudgetRowRepository.save(
        transactionBudgetRowMapper.toEntity(transactionBudgetRowDto));
  }

  public void addTransactionBudgetRow(Transaction transaction, BudgetRowDto budgetRowDto) {
    TransactionBudgetRow transactionBudgetRow = new TransactionBudgetRow();
    transactionBudgetRow.setBudgetRow(budgetRowMapper.toEntity(budgetRowDto));
    transactionBudgetRow.getTransactions().add(transaction);
    transactionBudgetRowRepository.save(transactionBudgetRow);
  }

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
