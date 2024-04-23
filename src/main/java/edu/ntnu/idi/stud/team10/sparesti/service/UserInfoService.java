package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserInfoMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserInfoRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
import jakarta.transaction.Transactional;

/** Service class for UserInfo. */
@Service
public class UserInfoService {
  private final UserRepository userRepository;
  private final UserInfoRepository userInfoRepository;
  private final UserInfoMapper userInfoMapper;

  /**
   * Constructor for UserInfoService. Dependency injection is handled by Spring.
   *
   * @param userRepository (UserRepository) Repository for User.
   * @param userInfoRepository (UserInfoRepository) Repository for UserInfo.
   * @param userInfoMapper (UserInfoMapper) Mapper for UserInfo.
   */
  @Autowired
  public UserInfoService(
      UserRepository userRepository,
      UserInfoRepository userInfoRepository,
      UserInfoMapper userInfoMapper) {
    this.userRepository = userRepository;
    this.userInfoRepository = userInfoRepository;
    this.userInfoMapper = userInfoMapper;
  }

  /**
   * Creates a new record of UserInfo.
   *
   * @param userInfoDto (UserInfoDto) A Dto representing the user info.
   * @return The stored UserInfo, as a Dto.
   */
  public UserInfoDto createUserInfo(UserInfoDto userInfoDto) {
    UserInfo userInfo = userInfoMapper.toEntity(userInfoDto);
    userInfo
        .getBudgetingProducts()
        .forEach(budgetingProduct -> budgetingProduct.setUserInfo(userInfo));
    User user = findUserById(userInfoDto.getUserId());
    userInfo.setUser(user);
    return userInfoMapper.toDto(userInfoRepository.save(userInfo));
  }

  /**
   * Gets the UserInfo for a user identified by their id.
   *
   * @param userId (Long) The id of the user.
   * @return The UserInfo, as a Dto.
   * @throws NotFoundException if the user info is not found.
   */
  public UserInfoDto getUserInfoByUserId(Long userId) {
    Optional<UserInfo> userInfo = userInfoRepository.findByUserId(userId);
    if (userInfo.isEmpty()) {
      throw new NotFoundException("User info not found");
    }
    return userInfoMapper.toDto(userInfo.get());
  }

  /**
   * Gets the UserInfo for a user identified by their email.
   *
   * @param email (String) The email of the user.
   * @return The UserInfo, as a Dto.
   * @throws NotFoundException if the user info is not found.
   */
  @Transactional
  public UserInfoDto getUserInfoByEmail(String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException("User not found"));
    return userInfoRepository
        .findByUserId(user.getId())
        .map(userInfoMapper::toDto)
        .orElseThrow(() -> new NotFoundException("User info not found"));
  }

  /**
   * Finds a User by their id.
   *
   * @param userId (Long) The id of the user.
   * @return The User, if it exists.
   * @throws NotFoundException if the user is not found.
   */
  private User findUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));
  }
}
