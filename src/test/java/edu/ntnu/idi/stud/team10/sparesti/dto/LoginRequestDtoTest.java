package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginRequestDtoTest {
  private LoginRequestDto loginRequestDto;

  @BeforeEach
  public void setUp() {
    loginRequestDto = new LoginRequestDto();
    loginRequestDto.setUsername("testUser");
    loginRequestDto.setPassword("testPassword");
    loginRequestDto.setRole("testRole");
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetUsername() {
      loginRequestDto.setUsername("newUser");
      assertEquals("newUser", loginRequestDto.getUsername());
    }

    @Test
    void getAndSetPassword() {
      loginRequestDto.setPassword("newPassword");
      assertEquals("newPassword", loginRequestDto.getPassword());
    }

    @Test
    void getAndSetRole() {
      loginRequestDto.setRole("newRole");
      assertEquals("newRole", loginRequestDto.getRole());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private LoginRequestDto anotherLoginRequestDto;

    @BeforeEach
    void setUp() {
      anotherLoginRequestDto = new LoginRequestDto();
      anotherLoginRequestDto.setUsername("testUser");
      anotherLoginRequestDto.setPassword("testPassword");
      anotherLoginRequestDto.setRole("testRole");
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(loginRequestDto, anotherLoginRequestDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherLoginRequestDto.setUsername("newUser");
      assertNotEquals(loginRequestDto, anotherLoginRequestDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(loginRequestDto.hashCode(), anotherLoginRequestDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherLoginRequestDto.setUsername("newUser");
      assertNotEquals(loginRequestDto.hashCode(), anotherLoginRequestDto.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(loginRequestDto.toString());
  }
}
