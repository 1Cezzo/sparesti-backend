package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.ChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;

/** Mapper between challenge entity and challenge dto. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChallengeMapper {
  ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

  @SubclassMapping(source = ConsumptionChallengeDto.class, target = ConsumptionChallenge.class)
  @SubclassMapping(source = PurchaseChallengeDto.class, target = PurchaseChallenge.class)
  @SubclassMapping(source = SavingChallengeDto.class, target = SavingChallenge.class)
  Challenge toEntity(ChallengeDto dto);

  @SubclassMapping(source = ConsumptionChallenge.class, target = ConsumptionChallengeDto.class)
  @SubclassMapping(source = PurchaseChallenge.class, target = PurchaseChallengeDto.class)
  @SubclassMapping(source = SavingChallenge.class, target = SavingChallengeDto.class)
  ChallengeDto toDto(Challenge entity);
}
