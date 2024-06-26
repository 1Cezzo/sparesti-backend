package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.Budget;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserBudgetServiceTest {

  @InjectMocks private UserBudgetService userBudgetService;

  @Mock private UserRepository userRepository;

  @Mock private BudgetRepository budgetRepository;

  @Mock private BudgetRowRepository budgetRowRepository;

  @Mock private UserMapper userMapper;

  @Mock private BudgetMapper budgetMapper;

  @Mock private BudgetRowMapper budgetRowMapper;

  @BeforeEach
  public void setup() {
    when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
    when(userMapper.toEntity(any(UserDto.class))).thenReturn(new User());

    when(budgetMapper.toDto(any(Budget.class))).thenReturn(new BudgetDto());
    when(budgetMapper.toEntity(any(BudgetDto.class))).thenReturn(new Budget());

    when(budgetRowMapper.toDto(any(BudgetRow.class))).thenReturn(new BudgetRowDto());
    when(budgetRowMapper.toEntity(any(BudgetRowDto.class))).thenReturn(new BudgetRow());
  }

  @Test
  void testAddBudgetToUser() {
    // Arrange
    User user = new User();
    BudgetDto budgetDto = new BudgetDto();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(budgetRepository.save(any(Budget.class))).thenReturn(null);

    // Act
    userBudgetService.addBudgetToUser(1L, budgetDto);

    // Assert
    verify(userRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).save(any(Budget.class));
  }

  @Test
  void testEditBudgetRowInUserBudget() {
    // Arrange
    User user = new User();
    Budget budget = new Budget();
    budget.setUser(user); // Set the user to the budget
    BudgetRow budgetRow = new BudgetRow();
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(budgetRepository.findById(any(Long.class))).thenReturn(Optional.of(budget));
    when(budgetRowRepository.findById(any(Long.class))).thenReturn(Optional.of(budgetRow));

    // Act
    userBudgetService.editBudgetRowInUserBudget(1L, 1L, 1L, budgetRowDto);

    // Assert
    verify(userRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).findById(any(Long.class));
    verify(budgetRowRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void testAddBudgetRowToUserBudget() {
    // Arrange
    User user = new User();
    Budget budget = new Budget();
    budget.setUser(user); // Set the user to the budget
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(budgetRepository.findById(any(Long.class))).thenReturn(Optional.of(budget));

    // Act
    userBudgetService.addBudgetRowToUserBudget(1L, 1L, budgetRowDto);

    // Assert
    verify(userRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).save(any(Budget.class));
  }

  @Test
  void testDeleteBudgetRowFromUserBudget() {
    // Arrange
    User user = new User();
    Budget budget = new Budget();
    budget.setUser(user); // Set the user to the budget
    BudgetRow budgetRow = new BudgetRow();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(budgetRepository.findById(any(Long.class))).thenReturn(Optional.of(budget));
    when(budgetRowRepository.findById(any(Long.class))).thenReturn(Optional.of(budgetRow));

    // Act
    userBudgetService.deleteBudgetRowFromUserBudget(1L, 1L, 1L);

    // Assert
    verify(userRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).findById(any(Long.class));
    verify(budgetRowRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).save(any(Budget.class));
  }

  @Test
  void testDeleteBudgetFromUser() {
    // Arrange
    User user = new User();
    Budget budget = new Budget();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(budgetRepository.findById(any(Long.class))).thenReturn(Optional.of(budget));

    // Act
    userBudgetService.deleteBudgetFromUser(1L, 1L);

    // Assert
    verify(userRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).delete(any(Budget.class));
  }

  @Test
  void testGetAllBudgetsForUser() {
    // Arrange
    User user = new User();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

    // Act
    userBudgetService.getAllBudgetsForUser(1L);

    // Assert
    verify(userRepository, times(1)).findById(any(Long.class));
    verify(budgetRepository, times(1)).findByUser(any(User.class));
  }
}
