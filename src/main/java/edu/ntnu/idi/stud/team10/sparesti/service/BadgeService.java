package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;

/** Service for Badge entities */
@Service
public class BadgeService {
  private final BadgeRepository badgeRepository;

  @Autowired
  public BadgeService(BadgeRepository badgeRepository) {
    this.badgeRepository = badgeRepository;
  }

  /**
   * Creates a new Badge entity
   *
   * @param badgeDto (BadgeDto): the data transfer object representing the Badge to create
   * @return the created Badge
   */
  public Badge createBadge(BadgeDto badgeDto) {
    return badgeRepository.save(badgeDto.toEntity()); // unique id - should not need validation?
  }

  /**
   * Using its unique id, deletes a Badge entity from the repository.
   *
   * @param id (Long): The unique id of the Badge entity.
   * @return
   */
  public void deleteBadgeById(
      Long id) { // Will we need to delete user-badge relationships of the same id?
    // In that case, could return the id of the same badge to be passed on.
    try {
      badgeRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new IllegalArgumentException("Badge with id " + id + " not found...");
    }
  }

  /** Returns all stored badges. */
  public List<Badge> getAllBadges() {
    return badgeRepository.findAll();
  }

  /**
   * Returns an optional that includes a Badge entity with a given id - if the id exists in repo.
   *
   * @param id (Long): The unique id of the Badge entity.
   * @return the Badge if it exists, otherwise an empty Optional
   */
  public Optional<Badge> getBadgeById(Long id) {
    return badgeRepository.findById(id);
  }

  /**
   * Updates a Badge entity.
   *
   * @param id (Long): The unique id of the Badge.
   * @param badgeDto (BadgeDto): The data transfer object representing the Badge to alter.
   * @return the updated Badge.
   */
  public Badge updateBadge(Long id, BadgeDto badgeDto) {
    Optional<Badge> badgeOptional = badgeRepository.findById(id);
    if (badgeOptional.isPresent()) {
      Badge badge = badgeOptional.get();
      badge.setTitle(badgeDto.getTitle());
      badge.setDescription(badgeDto.getDescription());
      badge.setImageUrl(badgeDto.getImageUrl());
      return badgeRepository.save(badge);
    } else {
      throw new IllegalArgumentException("Badge with id " + id + " not found...");
    }
  }
}
