package edu.ntnu.idi.stud.team10.sparesti.mapper;

import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = BudgetingProductMapper.class)
public interface UserInfoMapper {
  UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

  @Mapping(source = "budgetingProducts", target = "budgetingProducts")
  @Mapping(target = "id", ignore = true)
  UserInfo toEntity(UserInfoDto userInfoDto);

  @Mapping(source = "budgetingProducts", target = "budgetingProducts")
  @Mapping(target = "userId", source = "user.id")
  UserInfoDto toDto(UserInfo userInfo);

  @Mapping(target = "id", ignore = true)
  default void updateFromDto(UserInfoDto userInfoDto, @MappingTarget UserInfo userInfo) {
    if (userInfoDto == null || userInfo == null) {
      return;
    }
    Optional.ofNullable(userInfoDto.getDisplayName()).ifPresent(userInfo::setDisplayName);
    Optional.ofNullable(userInfoDto.getFirstName()).ifPresent(userInfo::setFirstName);
    Optional.ofNullable(userInfoDto.getLastName()).ifPresent(userInfo::setLastName);
    Optional.ofNullable(userInfoDto.getDateOfBirth()).ifPresent(userInfo::setDateOfBirth);
    Optional.ofNullable(userInfoDto.getOccupationStatus()).ifPresent(userInfo::setOccupationStatus);
    Optional.ofNullable(userInfoDto.getMotivation()).ifPresent(userInfo::setMotivation);
    Optional.ofNullable(userInfoDto.getIncome()).ifPresent(userInfo::setIncome);
    Optional.ofNullable(userInfoDto.getBudgetingProducts())
        .ifPresent(
            products ->
                userInfo.setBudgetingProducts(
                    products.stream()
                        .map(BudgetingProductMapper.INSTANCE::toEntity)
                        .collect(Collectors.toSet())));
    Optional.ofNullable(userInfoDto.getBudgetingLocations())
        .ifPresent(userInfo::setBudgetingLocations);
  }
}
