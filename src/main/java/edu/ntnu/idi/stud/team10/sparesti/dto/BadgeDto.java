package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import lombok.*;

/**
 * Data transfer object for Badge entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
    private long id;
    private String title;
    private String description;
    private String imageUrl;

    /**
     * Converts badge into DTO for data transfer.
     *
     * @param badge (Badge) The badge being converted
     */
    public BadgeDto(Badge badge) {
        this.id = badge.getId();
        this.title = badge.getTitle();
        this.description = badge.getDescription();
        this.imageUrl = badge.getImageUrl();
    }

    /** */
    public Badge toEntity() {
        return new Badge(id, title, description, imageUrl);
    }

}

