package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.Set;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import lombok.*;

/** Data transfer object for Badge entities */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
  private long id;
  private String name;
  private String description;
  private String imageUrl;
  private Set<User> users;

  /**
   * Converts badge into DTO for data transfer.
   *
   * @param badge (Badge) The badge being converted
   */
  public BadgeDto(Badge badge) {
    this.id = badge.getId();
    this.name = badge.getName();
    this.description = badge.getDescription();
    this.imageUrl = badge.getImageUrl();
  }

  /** Converts DTO to Badge entity. */
  public Badge toEntity() {
    Badge badge = new Badge();
    badge.setId(this.id);
    badge.setName(this.name);
    badge.setDescription(this.description);
    badge.setImageUrl(this.imageUrl);
    badge.setUsers(this.users);
    return new Badge(id, name, description, imageUrl, users);
  }
}
