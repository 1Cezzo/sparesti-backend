package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SavingsGoalServiceTest {

  @Mock private SavingsGoalRepository savingsGoalRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private SavingsGoalService savingsGoalService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  //  @Test
  //  public void testUpdateSavedAmount_ValidInput_SavedAmountUpdated() {
  //    SavingsGoal existingSavingsGoal = new SavingsGoal();
  //    existingSavingsGoal.setId(1L);
  //    existingSavingsGoal.setSavedAmount(500);
  //
  //    when(savingsGoalRepository.findById(existingSavingsGoal.getId()))
  //        .thenReturn(Optional.of(existingSavingsGoal));
  //    when(savingsGoalRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
  //
  //    double additionalAmount = 100;
  //    savingsGoalService.updateSavedAmount(existingSavingsGoal.getId(), additionalAmount);
  //
  //    assertEquals(600, existingSavingsGoal.getSavedAmount());
  //  }

  @Test
  public void testCreateSavingsGoal_ValidInput_ReturnsSavingsGoal() {
    User user = new User();
    user.setId(1L);
    SavingsGoalDto savingsGoalDTO = new SavingsGoalDto();
    savingsGoalDTO.setTargetAmount(1000);

    SavingsGoal savedSavingsGoal = new SavingsGoal();
    savedSavingsGoal.setId(1L);
    savedSavingsGoal.setTargetAmount(1000);

    when(savingsGoalRepository.save(any())).thenReturn(savedSavingsGoal);

    SavingsGoal createdSavingsGoal =
        savingsGoalService.createSavingsGoal(savingsGoalDTO, user.getId());

    assertNotNull(createdSavingsGoal);
    assertEquals(savedSavingsGoal.getId(), createdSavingsGoal.getId());
    assertEquals(savedSavingsGoal.getTargetAmount(), createdSavingsGoal.getTargetAmount());
  }

  //  @Test
  //  public void testGetAllSavingsGoalsForUser_ValidInput_ReturnsListOfSavingsGoalsDto() {
  //    User user = new User();
  //    user.setId(1L);
  //
  //    SavingsGoal savingsGoal1 = new SavingsGoal();
  //    savingsGoal1.setId(1L);
  //    savingsGoal1.setName("Savings Goal 1");
  //    savingsGoal1.setTargetAmount(1000);
  //
  //    SavingsGoal savingsGoal2 = new SavingsGoal();
  //    savingsGoal2.setId(2L);
  //    savingsGoal2.setName("Savings Goal 2");
  //    savingsGoal2.setTargetAmount(2000);
  //
  //    List<SavingsGoal> savingsGoals = new ArrayList<>();
  //    savingsGoals.add(savingsGoal1);
  //    savingsGoals.add(savingsGoal2);
  //
  //    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
  //    when(savingsGoalRepository.findByUserSavingsGoalsId(user.getId())).thenReturn(savingsGoals);
  //
  //    List<SavingsGoalDTO> savingsGoalDTOs =
  //        savingsGoalService.getAllSavingsGoalsForUser(user.getId());
  //
  //    assertNotNull(savingsGoalDTOs);
  //    assertEquals(2, savingsGoalDTOs.size());
  //    assertTrue(savingsGoalDTOs.stream().anyMatch(s -> s.getId().equals(savingsGoal1.getId())));
  //    assertTrue(savingsGoalDTOs.stream().anyMatch(s -> s.getId().equals(savingsGoal2.getId())));
  //  }

  @Test
  public void testUpdateSavingsGoal_ValidInput_SavingsGoalUpdated() {
    // Arrange
    SavingsGoalDto savingsGoalDTO = new SavingsGoalDto();
    savingsGoalDTO.setName("New Goal");
    savingsGoalDTO.setTargetAmount(2000);
    savingsGoalDTO.setSavedAmount(500);
    savingsGoalDTO.setMediaUrl("http://example.com/image.jpg");
    savingsGoalDTO.setDeadline(LocalDate.from(LocalDateTime.now().plusMonths(1)));

    SavingsGoal existingSavingsGoal = new SavingsGoal();
    existingSavingsGoal.setId(1L);
    existingSavingsGoal.setName("Old Goal");
    existingSavingsGoal.setTargetAmount(1000);
    existingSavingsGoal.setSavedAmount(200);

    when(savingsGoalRepository.findById(existingSavingsGoal.getId()))
        .thenReturn(Optional.of(existingSavingsGoal));
    when(savingsGoalRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    SavingsGoal updatedSavingsGoal =
        savingsGoalService.updateSavingsGoal(existingSavingsGoal.getId(), savingsGoalDTO);

    // Assert
    verify(savingsGoalRepository).save(any(SavingsGoal.class));
    assertEquals(savingsGoalDTO.getName(), updatedSavingsGoal.getName());
    assertEquals(savingsGoalDTO.getTargetAmount(), updatedSavingsGoal.getTargetAmount());
    assertEquals(savingsGoalDTO.getSavedAmount(), updatedSavingsGoal.getSavedAmount());
    assertEquals(savingsGoalDTO.getMediaUrl(), updatedSavingsGoal.getMediaUrl());
    assertEquals(
        savingsGoalDTO.getDeadline().atStartOfDay(),
        updatedSavingsGoal.getDeadline().atStartOfDay());
  }

  @Test
  public void testDeleteSavingsGoalById_ValidInput_SavingsGoalDeleted() {
    // Arrange
    Long id = 1L;
    SavingsGoal existingSavingsGoal = new SavingsGoal();
    existingSavingsGoal.setId(id);

    when(savingsGoalRepository.findById(id)).thenReturn(Optional.of(existingSavingsGoal));
    doNothing().when(savingsGoalRepository).deleteById(id);

    // Act
    savingsGoalService.deleteSavingsGoalById(id);

    // Assert
    verify(savingsGoalRepository).deleteById(id);
  }

  //  @Test
  //  public void testDeleteSavingsGoalFromUser_ValidInput_SavingsGoalDeletedFromUser() {
  //    // Arrange
  //    Long userId = 1L;
  //    Long savingsGoalId = 1L;
  //    User user = new User();
  //    user.setId(userId);
  //    SavingsGoal savingsGoal = new SavingsGoal();
  //    savingsGoal.setId(savingsGoalId);
  //    user.setSavingsGoals(new ArrayList<>(List.of(savingsGoal)));
  //
  //    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
  //    when(savingsGoalRepository.findById(savingsGoalId)).thenReturn(Optional.of(savingsGoal));
  //
  //    // Act
  //    savingsGoalService.deleteSavingsGoalFromUser(userId, savingsGoalId);
  //
  //    // Assert
  //    verify(savingsGoalRepository).delete(savingsGoal);
  //    assertFalse(user.getSavingsGoals().contains(savingsGoal));
  //  }

  //  @Test
  //  public void testAddSavingsGoalToUser_ValidInput_SavingsGoalAddedToUser() {
  //    // Arrange
  //    Long userId = 1L;
  //    User user = new User();
  //    user.setId(userId);
  //    SavingsGoalDTO savingsGoalDTO = new SavingsGoalDTO();
  //    savingsGoalDTO.setTargetAmount(1000);
  //
  //    SavingsGoal savingsGoal = savingsGoalDTO.toEntity();
  //    savingsGoal.setSavedAmount(0);
  //    savingsGoal.setCompleted(false);
  //
  //    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
  //    when(savingsGoalRepository.save(any())).thenReturn(savingsGoal);
  //
  //    // Act
  //    UserDto userDto = savingsGoalService.addSavingsGoalToUser(userId, savingsGoalDTO);
  //
  //    // Assert
  //    assertNotNull(userDto);
  //    assertEquals(userId, userDto.getId());
  //  }
}
