package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

/** Badge entity for storage in database */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "badges")
@EqualsAndHashCode(exclude ="users")
public class Badge {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false)
  private String title; // this and/or imageurl could theoretically be unique?

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @JsonIgnore
  @ManyToMany(mappedBy = "earnedBadges")
  Set<User> users;

  public void addUser(User user) {
    users.add(user);
  }

  public void removeUser(User user) {
    users.remove(user);
  }
}
