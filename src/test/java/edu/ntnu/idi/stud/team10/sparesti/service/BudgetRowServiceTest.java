package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BudgetRowServiceTest {

  @Mock private BudgetRowRepository budgetRowRepository;

  @Mock private BudgetRowMapper budgetRowMapper;

  @InjectMocks private BudgetRowService budgetRowService;

  @BeforeEach
  public void setUp() {
    when(budgetRowMapper.toEntity(any())).thenReturn(new BudgetRow());
  }

  @Test
  public void testCreateBudgetRow() {
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    BudgetRow budgetRow = new BudgetRow();
    when(budgetRowRepository.save(any())).thenReturn(budgetRow);

    BudgetRow result = budgetRowService.createBudgetRow(budgetRowDto);

    assertNotNull(result);
  }

  @Test
  public void testGetAllBudgetRows() {
    List<BudgetRow> budgetRows = new ArrayList<>();
    when(budgetRowRepository.findAll()).thenReturn(budgetRows);

    List<BudgetRow> result = budgetRowService.getAllBudgetRows();

    assertNotNull(result);
  }

  @Test
  public void testGetBudgetRowById() {
    BudgetRow budgetRow = new BudgetRow();
    when(budgetRowRepository.findById(any(Long.class))).thenReturn(Optional.of(budgetRow));

    Optional<BudgetRow> result = budgetRowService.getBudgetRowById(1L);

    assertNotNull(result);
  }

  @Test
  public void testUpdateBudgetRow() {
    BudgetRow budgetRow = new BudgetRow();
    when(budgetRowRepository.findById(any())).thenReturn(Optional.of(budgetRow));
    when(budgetRowRepository.save(any())).thenReturn(budgetRow);

    BudgetRowDto budgetRowDto = new BudgetRowDto();
    budgetRowDto.setName("New Name");
    budgetRowDto.setUsedAmount(100.0);
    budgetRowDto.setMaxAmount(200.0);
    budgetRowDto.setCategory("New Category");

    BudgetRow result = budgetRowService.updateBudgetRow(1L, budgetRowDto);

    assertNotNull(result);
  }

  @Test
  public void testDeleteBudgetRowById() {
    assertDoesNotThrow(() -> budgetRowService.deleteBudgetRowById(1L));
  }

  @Test
  public void testDeleteBudgetRowByIdExceptionHandling() {
    // Arrange
    Long id = 123L;
    doThrow(new EmptyResultDataAccessException(1)).when(budgetRowRepository).deleteById(id);

    // Act & Assert
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              budgetRowService.deleteBudgetRowById(id);
            });

    assertEquals("Budget row with ID " + id + " not found", exception.getMessage());

    // Verify that budgetRowRepository.deleteById was called once with the given ID
    verify(budgetRowRepository, times(1)).deleteById(id);
  }
}
