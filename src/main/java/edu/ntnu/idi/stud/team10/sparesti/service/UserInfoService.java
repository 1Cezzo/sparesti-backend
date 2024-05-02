package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserInfoMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetingProduct;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetingProductRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserInfoRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ConflictException;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
import jakarta.transaction.Transactional;

/** Service class for UserInfo. */
@Service
public class UserInfoService {
  private final UserRepository userRepository;
  private final UserInfoRepository userInfoRepository;
  private final UserInfoMapper userInfoMapper;
  private final BudgetingProductRepository budgetingProductRepository;

  /**
   * Constructor for UserInfoService. Dependency injection is handled by Spring.
   *
   * @param userRepository (UserRepository) Repository for User.
   * @param userInfoRepository (UserInfoRepository) Repository for UserInfo.
   * @param userInfoMapper (UserInfoMapper) Mapper for UserInfo.
   * @param budgetingProductRepository (BudgetingProductRepository) Repository for BudgetingProduct.
   */
  @Autowired
  public UserInfoService(
      UserRepository userRepository,
      UserInfoRepository userInfoRepository,
      UserInfoMapper userInfoMapper,
      BudgetingProductRepository budgetingProductRepository) {
    this.userRepository = userRepository;
    this.userInfoRepository = userInfoRepository;
    this.userInfoMapper = userInfoMapper;
    this.budgetingProductRepository = budgetingProductRepository;
  }

  /**
   * Creates a new record of UserInfo.
   *
   * @param userInfoDto (UserInfoDto) A Dto representing the user info.
   * @return The stored UserInfo, as a Dto.
   * @throws ConflictException if the user info already exists.
   * @throws NotFoundException if the user is not found.
   */
  public UserInfoDto createUserInfo(UserInfoDto userInfoDto) {
    if (userInfoRepository.existsByUserId(userInfoDto.getUserId())) {
      throw new ConflictException("User info already exists");
    }
    if (userInfoRepository.existsByDisplayName(userInfoDto.getDisplayName())) {
      throw new ConflictException("Display name is taken.");
    }
    UserInfo userInfo = userInfoMapper.toEntity(userInfoDto);
    User user = findUserById(userInfoDto.getUserId());
    userInfo.setUser(user);
    Set<BudgetingProduct> budgetingProducts = userInfo.getBudgetingProducts();
    userInfo.setBudgetingProducts(null);
    UserInfoDto savedUserInfo = userInfoMapper.toDto(userInfoRepository.save(userInfo));
    budgetingProducts.forEach(
        budgetingProduct -> {
          budgetingProduct.setUserInfo(userInfo);
          budgetingProductRepository.save(budgetingProduct);
        });
    return savedUserInfo;
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
   * Checks if a given email is associated with a user.
   *
   * @param email (String) The email to check.
   * @return {@code true} if the email is associated with a user, {@code false} otherwise.
   */
  public boolean userInfoExistsByEmail(String email) {
    User user = findUserByEmail(email);
    return userInfoRepository.existsByUserId(user.getId());
  }

  /**
   * Updates the UserInfo for a user with the fields in the given Dto.
   *
   * @param userId (Long) The id of the user.
   * @param userInfoDto (UserInfoDto) A Dto with all the fields to update.
   * @return The updated UserInfo, as a Dto.
   * @throws NotFoundException if the user info is not found.
   */
  public UserInfoDto updateUserInfo(Long userId, UserInfoDto userInfoDto) {
    UserInfo info =
        userInfoRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("User info not found"));
    Optional.ofNullable(userInfoDto.getBudgetingProducts())
        .ifPresent(dtos -> budgetingProductRepository.deleteAll(info.getBudgetingProducts()));
    userInfoMapper.updateFromDto(userInfoDto, info);
    Optional.ofNullable(info.getBudgetingProducts())
        .ifPresent(
            budgetingProducts -> {
              budgetingProducts.forEach(budgetingProduct -> budgetingProduct.setUserInfo(info));
              budgetingProductRepository.saveAll(budgetingProducts);
            });
    return userInfoMapper.toDto(userInfoRepository.save(info));
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

  /**
   * Finds a User by their email.
   *
   * @param email (String) The email of the user.
   * @return The User, if it exists.
   * @throws NotFoundException if the user is not found.
   */
  private User findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException("User not found"));
  }
}
