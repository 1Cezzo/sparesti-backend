package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;

/** Mapper between badge entity and badge dto. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BadgeMapper {
  BadgeMapper INSTANCE = Mappers.getMapper(BadgeMapper.class);

  Badge toEntity(BadgeDto badgeDto);

  BadgeDto toDto(Badge badge);
}
