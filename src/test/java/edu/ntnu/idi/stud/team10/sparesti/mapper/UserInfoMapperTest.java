package edu.ntnu.idi.stud.team10.sparesti.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserInfoMapperTest {

  private UserInfoMapper userInfoMapper;

  @BeforeEach
  public void setUp() {
    userInfoMapper = Mappers.getMapper(UserInfoMapper.class);
  }

  @Test
  public void shouldMapUserInfoToUserInfoDto() {
    // Given
    User user = new User();
    user.setId(1L);
    user.setPassword("testPassword");
    user.setEmail("test@example.com");
    user.setTotalSavings(1000.0);
    user.setProfilePictureUrl("https://example.com/profile.jpg");
    user.setCheckingAccountNr(123456L);
    user.setSavingsAccountNr(789012L);
    user.setChallenges(new ArrayList<>());
    user.setEarnedBadges(new HashSet<>());
    UserInfo userInfo =
        new UserInfo(
            1L,
            user,
            "JohnDoe",
            "John",
            "Doe",
            LocalDate.of(1985, 5, 15),
            OccupationStatus.EMPLOYED,
            3,
            50000,
            new HashSet<>(),
            Arrays.asList("New York", "Los Angeles"));

    // When
    UserInfoDto userInfoDto = userInfoMapper.toDto(userInfo);

    // Then
    assertNotNull(userInfoDto);
    assertEquals(userInfo.getUser().getId(), userInfoDto.getUserId());
    assertEquals(userInfo.getDisplayName(), userInfoDto.getDisplayName());
    assertEquals(userInfo.getFirstName(), userInfoDto.getFirstName());
    assertEquals(userInfo.getLastName(), userInfoDto.getLastName());
    assertEquals(userInfo.getDateOfBirth(), userInfoDto.getDateOfBirth());
    assertEquals(userInfo.getOccupationStatus(), userInfoDto.getOccupationStatus());
    assertEquals(userInfo.getMotivation(), userInfoDto.getMotivation());
    assertEquals(userInfo.getIncome(), userInfoDto.getIncome());
    assertEquals(userInfo.getBudgetingLocations(), userInfoDto.getBudgetingLocations());
  }

  @Test
  public void shouldMapUserInfoDtoToUserInfo() {
    // Given
    UserInfoDto userInfoDto =
        new UserInfoDto(
            null,
            1L,
            "JohnDoe",
            "John",
            "Doe",
            LocalDate.of(1985, 5, 15),
            OccupationStatus.EMPLOYED,
            3,
            50000,
            new HashSet<>(),
            Arrays.asList("New York", "Los Angeles"));

    // When
    UserInfo userInfo = userInfoMapper.toEntity(userInfoDto);

    // Then
    assertNotNull(userInfo);
    assertNull(userInfo.getId(), "ID should be ignored");
    assertNull(userInfo.getUser(), "User relation is not mapped and should be null");
    assertEquals(userInfoDto.getDisplayName(), userInfo.getDisplayName());
    assertEquals(userInfoDto.getFirstName(), userInfo.getFirstName());
    assertEquals(userInfoDto.getLastName(), userInfo.getLastName());
    assertEquals(userInfoDto.getDateOfBirth(), userInfo.getDateOfBirth());
    assertEquals(userInfoDto.getOccupationStatus(), userInfo.getOccupationStatus());
    assertEquals(userInfoDto.getMotivation(), userInfo.getMotivation());
    assertEquals(userInfoDto.getIncome(), userInfo.getIncome());
    assertEquals(userInfoDto.getBudgetingLocations(), userInfo.getBudgetingLocations());
  }

  @Test
  public void shouldUpdateUserInfoFromDto() {
    // Given
    UserInfo userInfo = new UserInfo();
    UserInfoDto userInfoDto =
        new UserInfoDto(
            null,
            1L,
            "UpdatedName",
            "UpdatedFirst",
            "UpdatedLast",
            LocalDate.of(1990, 1, 1),
            OccupationStatus.UNEMPLOYED,
            5,
            100000,
            new HashSet<>(),
            Arrays.asList("San Francisco"));

    // When
    userInfoMapper.updateFromDto(userInfoDto, userInfo);

    // Then
    assertEquals(userInfoDto.getDisplayName(), userInfo.getDisplayName());
    assertEquals(userInfoDto.getFirstName(), userInfo.getFirstName());
    assertEquals(userInfoDto.getLastName(), userInfo.getLastName());
    assertEquals(userInfoDto.getDateOfBirth(), userInfo.getDateOfBirth());
    assertEquals(userInfoDto.getOccupationStatus(), userInfo.getOccupationStatus());
    assertEquals(userInfoDto.getMotivation(), userInfo.getMotivation());
  }
}
