package edu.ntnu.idi.stud.team10.sparesti.dto;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import lombok.*;

import java.util.Set;

/** Data transfer object for Badge entities */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
  private long id;
  private String title;
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
    this.title = badge.getTitle();
    this.description = badge.getDescription();
    this.imageUrl = badge.getImageUrl();
  }

  /** */
  public Badge toEntity() {
    Badge badge = new Badge();
    badge.setId(this.id);
    badge.setTitle(this.title);
    badge.setDescription(this.description);
    badge.setImageUrl(this.imageUrl);
    badge.setUsers(this.users);
    return new Badge(id, title, description, imageUrl, users);
  }
}
