package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserResponseDtoTest {

  @Test
  public void testConstructorAndGetters() {
    String displayName = "John Doe";
    String firstName = "John";
    String lastName = "Doe";
    String email = "john.doe@example.com";
    String pictureUrl = "https://example.com/johndoe.jpg";
    List<String> badges = new ArrayList<>();
    badges.add("Badge1");
    badges.add("Badge2");
    double totalSavings = 1000.50;

    UserResponseDto userResponseDto =
        new UserResponseDto(
            displayName, firstName, lastName, email, pictureUrl, badges, totalSavings);

    assertNotNull(userResponseDto);
    assertEquals(displayName, userResponseDto.getDisplayName());
    assertEquals(firstName, userResponseDto.getFirstName());
    assertEquals(lastName, userResponseDto.getLastName());
    assertEquals(email, userResponseDto.getEmail());
    assertEquals(pictureUrl, userResponseDto.getPictureUrl());
    assertEquals(badges, userResponseDto.getBadges());
    assertEquals(totalSavings, userResponseDto.getTotalSavings());
  }

  @Test
  public void testEqualsAndHashCode() {
    UserResponseDto user1 =
        new UserResponseDto(
            "John Doe",
            "John",
            "Doe",
            "john.doe@example.com",
            "https://example.com/johndoe.jpg",
            List.of("Badge1", "Badge2"),
            1000.50);
    UserResponseDto user2 =
        new UserResponseDto(
            "John Doe",
            "John",
            "Doe",
            "john.doe@example.com",
            "https://example.com/johndoe.jpg",
            List.of("Badge1", "Badge2"),
            1000.50);
    UserResponseDto user3 =
        new UserResponseDto(
            "Jane Doe",
            "Jane",
            "Doe",
            "jane.doe@example.com",
            "https://example.com/janedoe.jpg",
            List.of("Badge1", "Badge2"),
            1500.75);

    assertEquals(user1, user2);
    assertNotEquals(user1, user3);
    assertEquals(user1.hashCode(), user2.hashCode());
    assertNotEquals(user1.hashCode(), user3.hashCode());
  }

  @Test
  public void testSetterAndGetters() {
    UserResponseDto user = new UserResponseDto();
    user.setDisplayName("John Doe");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setEmail("john.doe@example.com");
    user.setPictureUrl("https://example.com/johndoe.jpg");
    List<String> badges = new ArrayList<>();
    badges.add("Badge1");
    badges.add("Badge2");
    user.setBadges(badges);
    user.setTotalSavings(1000.50);

    assertEquals("John Doe", user.getDisplayName());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals("john.doe@example.com", user.getEmail());
    assertEquals("https://example.com/johndoe.jpg", user.getPictureUrl());
    assertEquals(badges, user.getBadges());
    assertEquals(1000.50, user.getTotalSavings());
  }

  @Test
  public void testConstructor() {
    String displayName = "John Doe";
    String firstName = "John";
    String lastName = "Doe";
    String email = "john.doe@example.com";
    String pictureUrl = "https://example.com/johndoe.jpg";
    List<String> badges = List.of("Badge1", "Badge2");
    double totalSavings = 1000.50;

    UserResponseDto userResponseDto =
        new UserResponseDto(
            displayName, firstName, lastName, email, pictureUrl, badges, totalSavings);

    assertEquals(displayName, userResponseDto.getDisplayName());
    assertEquals(firstName, userResponseDto.getFirstName());
    assertEquals(lastName, userResponseDto.getLastName());
    assertEquals(email, userResponseDto.getEmail());
    assertEquals(pictureUrl, userResponseDto.getPictureUrl());
    assertEquals(badges, userResponseDto.getBadges());
    assertEquals(totalSavings, userResponseDto.getTotalSavings());
  }

  @Test
  public void testToString() {
    String displayName = "John Doe";
    String firstName = "John";
    String lastName = "Doe";
    String email = "john.doe@example.com";
    String pictureUrl = "https://example.com/johndoe.jpg";
    List<String> badges = List.of("Badge1", "Badge2");
    double totalSavings = 1000.50;

    UserResponseDto userResponseDto =
        new UserResponseDto(
            displayName, firstName, lastName, email, pictureUrl, badges, totalSavings);

    String expectedToString =
        "UserResponseDto(displayName=John Doe, firstName=John, lastName=Doe, email=john.doe@example.com, pictureUrl=https://example.com/johndoe.jpg, badges=[Badge1, Badge2], totalSavings=1000.5)";
    assertEquals(expectedToString, userResponseDto.toString());
  }
}
