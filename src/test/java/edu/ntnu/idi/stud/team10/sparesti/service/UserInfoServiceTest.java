package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserInfoMapper;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetingProductRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserInfoRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ConflictException;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserInfoServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private UserInfoRepository userInfoRepository;
  @Mock private UserInfoMapper userInfoMapper;
  @Mock private BudgetingProductRepository budgetingProductRepository;

  @InjectMocks private UserInfoService userInfoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createUserInfo_WhenUserInfoExists_ShouldThrowConflictException() {
    // Arrange
    UserInfoDto userInfoDto = new UserInfoDto();
    userInfoDto.setUserId(1L);

    when(userInfoRepository.existsByUserId(userInfoDto.getUserId())).thenReturn(true);

    // Act & Assert
    assertThrows(ConflictException.class, () -> userInfoService.createUserInfo(userInfoDto));
    verify(userInfoRepository, never()).save(any());
  }

  @Test
  void createUserInfo_WhenUserNotFound_ShouldThrowNotFoundException() {
    // Arrange
    UserInfoDto userInfoDto = new UserInfoDto();
    userInfoDto.setUserId(1L);

    when(userInfoRepository.existsByUserId(userInfoDto.getUserId())).thenReturn(false);
    when(userRepository.findById(userInfoDto.getUserId())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NotFoundException.class, () -> userInfoService.createUserInfo(userInfoDto));
    verify(userInfoRepository, never()).save(any());
  }
}
