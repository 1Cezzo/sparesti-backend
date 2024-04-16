package edu.ntnu.idi.stud.team10.sparesti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDto addUser(UserDto userDto) {
    return null;
  }

  public UserDto getUserById(Long id) {
    return null;
  }

  public UserDto updateUser(UserDto userDto) {
    return null;
  }

  public void deleteUser(Long id) {}

  public void getUserByUsername(String username) {}
}
