package edu.ntnu.idi.stud.team10.sparesti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserInfoMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserInfoRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

@Service
public class UserInfoService {
  private final UserRepository userRepository;
  private final UserInfoRepository userInfoRepository;
  private final UserInfoMapper userInfoMapper;

  @Autowired
  public UserInfoService(
      UserRepository userRepository,
      UserInfoRepository userInfoRepository,
      UserInfoMapper userInfoMapper) {
    this.userRepository = userRepository;
    this.userInfoRepository = userInfoRepository;
    this.userInfoMapper = userInfoMapper;
  }

  public UserInfoDto createUserInfo(UserInfoDto userInfoDto) {
    UserInfo userInfo = userInfoMapper.toEntity(userInfoDto);
    userInfo
        .getBudgetingProducts()
        .forEach(budgetingProduct -> budgetingProduct.setUserInfo(userInfo));
    User user = findUserById(userInfoDto.getUserId());
    userInfo.setUser(user);
    UserInfo debug = userInfoRepository.save(userInfo);
    return userInfoMapper.toDto(debug);
  }

  private User findUserById(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));
  }
}
