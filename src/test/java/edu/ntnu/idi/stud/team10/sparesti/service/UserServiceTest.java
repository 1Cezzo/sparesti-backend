package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

  @Mock private UserRepository userRepository;

  private UserService userService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userService = new UserService(userRepository, null, null, null);
  }

  @Test
  public void testAddUser() {
    UserDto userDto = new UserDto();
    userDto.setPassword("password");
    User user = new User(userDto);
    when(userRepository.save(any())).thenReturn(user);

    UserDto result = userService.addUser(userDto);

    assertNotNull(result);
  }

  @Test
  public void testGetUserById() {
    User user = new User();
    when(userRepository.findById(any())).thenReturn(Optional.of(user));

    UserDto result = userService.getUserById(1L);

    assertNotNull(result);
  }

  @Test
  public void testUpdateUser() {
    User user = new User();
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);

    UserDto userDto = new UserDto();
    userDto.setId(1L);
    userDto.setPassword("newPassword");
    userDto.setEmail("newEmail");
  }
}
