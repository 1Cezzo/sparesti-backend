package edu.ntnu.idi.stud.team10.sparesti.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.model.User;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

  private UserMapper userMapper;

  @BeforeEach
  public void setUp() {
    userMapper = UserMapper.INSTANCE;
  }

  @Test
  public void shouldMapUserToUserDto_IgnorePassword() {
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
    // When
    UserDto userDto = userMapper.toDto(user);
    // Then
    assertNotNull(userDto);
    assertNull(userDto.getPassword(), "Password should be null");
    assertEquals(user.getId(), userDto.getId());
    assertEquals(user.getEmail(), userDto.getEmail());
    assertEquals(user.getProfilePictureUrl(), userDto.getProfilePictureUrl());
    assertEquals(user.getLoginStreak(), userDto.getLoginStreak());
    assertEquals(user.getLastLogin(), userDto.getLastLogin());
    assertEquals(user.getCheckingAccountNr(), userDto.getCheckingAccountNr());
    assertEquals(user.getSavingsAccountNr(), userDto.getSavingsAccountNr());
    assertEquals(user.getTotalSavings(), userDto.getTotalSavings());
  }

  @Test
  public void shouldMapUserDtoToUser() {
    // Given
    UserDto userDto =
        new UserDto(
            1L,
            null,
            "user@example.com",
            "url.com/pic.jpg",
            5,
            LocalDate.now(),
            123456789L,
            987654321L,
            1000.0,
            null);

    // When
    User user = userMapper.toEntity(userDto);

    // Then
    assertNotNull(user);
    assertEquals(userDto.getId(), user.getId());
    assertEquals(userDto.getEmail(), user.getEmail());
    assertEquals(userDto.getProfilePictureUrl(), user.getProfilePictureUrl());
    assertEquals(userDto.getLoginStreak(), user.getLoginStreak());
    assertEquals(userDto.getLastLogin(), user.getLastLogin());
    assertEquals(userDto.getCheckingAccountNr(), user.getCheckingAccountNr());
    assertEquals(userDto.getSavingsAccountNr(), user.getSavingsAccountNr());
    assertEquals(userDto.getTotalSavings(), user.getTotalSavings());
    assertNull(user.getPassword(), "Password should be null as it is not mapped");
  }
}
