package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

/** Service for Badge entities */
@Service
public class BadgeService {
  private final BadgeRepository badgeRepository;
  private final UserRepository userRepository;

  @Autowired
  public BadgeService(BadgeRepository badgeRepository, UserRepository userRepository) {
    this.badgeRepository = badgeRepository;
    this.userRepository = userRepository;
  }

  /**
   * Creates a new Badge entity
   *
   * @param badgeDto (BadgeDto): the data transfer object representing the Badge to create
   * @return the created Badge
   */
  public BadgeDto createBadge(BadgeDto badgeDto) {
    badgeRepository.save(badgeDto.toEntity()); // unique id - should not need validation?
    return badgeDto;
  }

  /**
   * Deletes a Badge by its id, and removes the badge from all users that have it.
   *
   * @param id (Long) The unique id of the Badge entity.
   * @throws NotFoundException if the badge is not found.
   */
  public void deleteBadgeById(Long id) {
    badgeRepository
        .findById(id)
        .ifPresentOrElse(
            badge -> {
              badge.getUsers().forEach(user -> user.getEarnedBadges().remove(badge));
              userRepository.saveAll(badge.getUsers());
              badgeRepository.delete(badge);
            },
            () -> {
              throw new NotFoundException("Badge with id " + id + " not found");
            });
  }

  /**
   * Gets all badges.
   *
   * @return a list of all badges.
   */
  public List<Badge> getAllBadges() {
    return badgeRepository.findAll();
  }

  /**
   * Counts how many Users have earned a certain badge
   *
   * @param badgeId (Long) the Badge's id being checked.
   * @return The amount of Users with the badge being checked.
   */
  public long countUsersWithBadge(Long badgeId) {
    return userRepository.countByBadgeId(
        badgeId); // could be put into getBadgeRarity to simplify/shorten it.
  }

  /**
   * Gets a Badge by its id.
   *
   * @param id (Long): The unique id of the Badge entity.
   * @return a DTO representing the Badge.
   * @throws NotFoundException if the badge is not found.
   */
  public BadgeDto getBadgeById(Long id) {
    Badge badge =
        badgeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Badge of id " + id + " not found"));
    return new BadgeDto(badge);
  }

  /**
   * Gets a Badge by its name.
   *
   * @param name (String): The name of the Badge entity.
   * @return a DTO representing the Badge.
   * @throws NotFoundException if the badge is not found.
   */
  public BadgeDto getBadgeByName(String name) {
    Badge badge =
        badgeRepository
            .findByName(name)
            .orElseThrow(() -> new NotFoundException("Badge of name " + name + " not found"));
    return new BadgeDto(badge);
  }

  /**
   * Updates a Badge entity.
   *
   * @param id (Long): The unique id of the Badge.
   * @param badgeDto (BadgeDto): The data transfer object representing the Badge to alter.
   * @return the updated Badge.
   * @throws NotFoundException If the badge id is not found.
   */
  public BadgeDto updateBadge(Long id, BadgeDto badgeDto) {
    Optional<Badge> badgeOptional = badgeRepository.findById(id);
    if (badgeOptional.isPresent()) {
      Badge badge = badgeOptional.get();
      badge.setName(badgeDto.getName());
      badge.setDescription(badgeDto.getDescription());
      badge.setImageUrl(badgeDto.getImageUrl());
      badgeRepository.save(badge);
      return new BadgeDto(badge);
    } else {
      throw new NotFoundException("Badge with id " + id + " not found...");
    }
  }

  /**
   * Calculates the percentage of users that have acquired a certain badge of badgeId.
   *
   * @param badgeId (Long): The id of the badge being checked.
   * @return The percentage of users that have the badge.
   * @throws NotFoundException If the badge id is not found.
   */
  public double findBadgeRarity(Long badgeId) { // unsure if this should be in UserService
    if (userRepository.count() == 0) {
      return 100.0;
    }
    if (badgeRepository.findById(badgeId).isPresent()) {
      double ratio =
          (double) countUsersWithBadge(badgeId)
              / userRepository
                  .count(); // Theoretically need to check if user count is zero to avoid division
      // by zero
      return ratio * 100; // percentage conversion
    } else {
      throw new NotFoundException("Badge with id " + badgeId + " not found...");
    }
  }

  /** Deletes all badges. */
  @Transactional
  public void deleteAllBadges() {
    badgeRepository.deleteAll();
  }
}
