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
import edu.ntnu.idi.stud.team10.sparesti.model.UserSavingsGoal;
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
    savingsGoal.setSavedAmount(50.0);
    savingsGoal.setTargetAmount(100.0);
    savingsGoal.setDeadline(LocalDate.now().plusMonths(1));
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

  @Test
  void testAddSavingsGoalToUser_ValidInput_SavingsGoalAdded() {
    Long userId = 1L;
    Long savingsGoalId = 1L;

    User user = new User();
    user.setId(userId);

    SavingsGoal savingsGoal = new SavingsGoal();
    savingsGoal.setId(savingsGoalId);
    savingsGoal.setTargetAmount(1000.0);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(savingsGoalRepository.findById(savingsGoalId)).thenReturn(Optional.of(savingsGoal));

    savingsGoalService.addSavingsGoalToUser(userId, savingsGoalId);

    verify(userSavingsGoalRepository).save(any(UserSavingsGoal.class));
  }

  @Test
  void testGetAllSavingsGoalsForUser_ValidInput_ReturnsSavingsGoals() {
    Long userId = 1L;

    User user = new User();
    user.setId(userId);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    savingsGoalService.getAllSavingsGoalsForUser(userId);

    verify(userSavingsGoalRepository).findByUserId(userId);
  }

  @Test
  void testDeleteSavingsGoalFromUser_ValidInput_SavingsGoalDeleted() {
    Long userId = 1L;
    Long savingsGoalId = 1L;

    User user = new User();
    user.setId(userId);

    SavingsGoal savingsGoal = new SavingsGoal();
    savingsGoal.setId(savingsGoalId);

    UserSavingsGoal userSavingsGoal = new UserSavingsGoal();
    userSavingsGoal.setUser(user);
    userSavingsGoal.setSavingsGoal(savingsGoal);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(savingsGoalRepository.findById(savingsGoalId)).thenReturn(Optional.of(savingsGoal));
    when(userSavingsGoalRepository.findByUserAndSavingsGoal(user, savingsGoal))
        .thenReturn(Optional.of(userSavingsGoal));

    savingsGoalService.deleteSavingsGoalFromUser(userId, savingsGoalId);

    verify(userSavingsGoalRepository).delete(userSavingsGoal);
  }

  @Test
  void testGetUsersBySavingsGoal_ValidInput_ReturnsUsers() {
    Long savingsGoalId = 1L;

    SavingsGoal savingsGoal = new SavingsGoal();
    savingsGoal.setId(savingsGoalId);

    when(savingsGoalRepository.findById(savingsGoalId)).thenReturn(Optional.of(savingsGoal));

    savingsGoalService.getUsersBySavingsGoal(savingsGoalId);

    verify(userSavingsGoalRepository).findBySavingsGoal(savingsGoal);
  }

  @Test
  void testHasActiveSavingsGoal_ValidInput_ReturnsBoolean() {
    Long userId = 1L;

    savingsGoalService.hasActiveSavingsGoal(userId);

    verify(userSavingsGoalRepository).findByUserId(userId);
  }

  @Test
  void testHasCompletedSavingsGoal_ValidInput_ReturnsBoolean() {
    Long userId = 1L;

    savingsGoalService.hasCompletedSavingsGoal(userId);

    verify(userSavingsGoalRepository).findByUserId(userId);
  }

  @Test
  void testHasSharedSavingsGoal_ValidInput_ReturnsBoolean() {
    Long userId = 1L;

    savingsGoalService.hasSharedSavingsGoal(userId);

    verify(userSavingsGoalRepository).findByUserId(userId);
  }

  @Test
  void testHasCreatedSavingsGoal_ValidInput_ReturnsBoolean() {
    Long userId = 1L;

    savingsGoalService.hasCreatedSavingsGoal(userId);

    verify(savingsGoalRepository).findSavingsGoalByAuthorId(userId);
  }
}
