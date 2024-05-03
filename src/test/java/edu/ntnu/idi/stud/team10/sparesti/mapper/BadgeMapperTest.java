package edu.ntnu.idi.stud.team10.sparesti.mapper;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BadgeMapperTest {

    private BadgeMapper badgeMapper;

    @BeforeEach
    public void setUp() {
        badgeMapper = Mappers.getMapper(BadgeMapper.class);
    }

    @Test
    public void shouldMapBadgeToBadgeDto() {
        // Given
        Badge badge = new Badge(1L, "Achiever", "Awarded for achieving something exceptional", "http://example.com/image.png");

        // When
        BadgeDto badgeDto = badgeMapper.toDto(badge);

        // Then
        assertNotNull(badgeDto);
        assertEquals(badge.getId(), badgeDto.getId());
        assertEquals(badge.getName(), badgeDto.getName());
        assertEquals(badge.getDescription(), badgeDto.getDescription());
        assertEquals(badge.getImageUrl(), badgeDto.getImageUrl());
    }

    @Test
    public void shouldMapBadgeDtoToBadge() {
        // Given
        BadgeDto badgeDto = new BadgeDto(1L, "Achiever", "Awarded for achieving something exceptional", "http://example.com/image.png");

        // When
        Badge badge = badgeMapper.toEntity(badgeDto);

        // Then
        assertNotNull(badge);
        assertEquals(badgeDto.getId(), badge.getId());
        assertEquals(badgeDto.getName(), badge.getName());
        assertEquals(badgeDto.getDescription(), badge.getDescription());
        assertEquals(badgeDto.getImageUrl(), badge.getImageUrl());
    }
}