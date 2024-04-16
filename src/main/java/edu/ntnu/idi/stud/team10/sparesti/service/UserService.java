package edu.ntnu.idi.stud.team10.sparesti.service;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public void deleteUser(Long id) {
  }

  public UserDto getUserByUsername(String username) {
    return null;
  }
}
