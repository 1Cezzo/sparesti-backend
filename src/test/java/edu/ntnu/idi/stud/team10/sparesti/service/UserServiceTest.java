package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private BCryptPasswordEncoder passwordEncoder;

  @Mock private UserMapper userMapper;

  @InjectMocks private UserService userService;

  @BeforeEach
  public void setUp() {
    when(userMapper.toDto(any())).thenReturn(new UserDto());
    when(userMapper.toEntity(any())).thenReturn(new User());
  }

  @Test
  public void testAddUser() {
    UserDto userDto = new UserDto();
    userDto.setPassword("password");
    User user = UserMapper.INSTANCE.toEntity(userDto);
    when(userRepository.save(any())).thenReturn(user);

    UserDto result = userService.addUser(userDto);

    assertNotNull(result);
  }

  @Test
  public void testGetUserById() {
    User user = new User();
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

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
