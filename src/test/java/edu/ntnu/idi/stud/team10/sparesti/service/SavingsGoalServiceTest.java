package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.SavingsGoalMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserSavingsGoalRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SavingsGoalServiceTest {

  @Mock private SavingsGoalRepository savingsGoalRepository;

  @Mock private UserRepository userRepository;

  @Mock private SavingsGoalMapper savingsGoalMapper;

  @Mock private User user;

  @Mock private SavingsGoal savingsGoal;

  @Mock private UserSavingsGoalRepository userSavingsGoalRepository;

  @InjectMocks private SavingsGoalService savingsGoalService;

  @BeforeEach
  public void setUp() {
    when(savingsGoalMapper.toEntity(any())).thenReturn(new SavingsGoal());
    when(savingsGoalMapper.toDto(any())).thenReturn(new SavingsGoalDto());
  }

  @Test
  void testCreateSavingsGoal_ValidInput_ReturnsSavingsGoal() {
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

  @Test
  void testUpdateSavingsGoal_ValidInput_SavingsGoalUpdated() {
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
  void testDeleteSavingsGoalById_ValidInput_SavingsGoalDeleted() {
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

  @Test
  void testGetSavingsGoalById_ExistingId_ReturnsSavingsGoal() {
    // Arrange
    Long id = 1L;
    SavingsGoal savingsGoal = new SavingsGoal();
    savingsGoal.setId(id);

    when(savingsGoalRepository.findById(id)).thenReturn(Optional.of(savingsGoal));

    // Act
    SavingsGoal retrievedSavingsGoal = savingsGoalService.getSavingsGoalById(id);

    // Assert
    assertNotNull(retrievedSavingsGoal);
    assertEquals(savingsGoal.getId(), retrievedSavingsGoal.getId());
  }

  @Test
  void testGetSavingsGoalById_NonExistingId_ThrowsNotFoundException() {
    // Arrange
    Long id = 1L;

    when(savingsGoalRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NotFoundException.class, () -> savingsGoalService.getSavingsGoalById(id));
  }

  @Test
  void testUpdateSavingsGoal_NonExistingId_ThrowsNotFoundException() {
    // Arrange
    Long id = 1L;
    SavingsGoalDto savingsGoalDTO = new SavingsGoalDto();

    when(savingsGoalRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        NotFoundException.class, () -> savingsGoalService.updateSavingsGoal(id, savingsGoalDTO));
  }

  @Test
  void testUpdateSavingsGoal_NullDto_ThrowsIllegalArgumentException() {
    // Arrange
    Long id = 1L;

    SavingsGoal existingSavingsGoal = new SavingsGoal();
    existingSavingsGoal.setId(id);

    when(savingsGoalRepository.findById(existingSavingsGoal.getId()))
        .thenReturn(Optional.of(existingSavingsGoal));

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class, () -> savingsGoalService.updateSavingsGoal(id, null));
  }
}
