package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResetRequestDtoTest {
  private ResetRequestDto resetRequestDto;

  @BeforeEach
  public void setUp() {
    resetRequestDto = new ResetRequestDto();
    resetRequestDto.setUsername("testUser");
    resetRequestDto.setPassword("testPassword");
    resetRequestDto.setToken("testToken");
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetUsername() {
      resetRequestDto.setUsername("newUser");
      assertEquals("newUser", resetRequestDto.getUsername());
    }

    @Test
    void getAndSetPassword() {
      resetRequestDto.setPassword("newPassword");
      assertEquals("newPassword", resetRequestDto.getPassword());
    }

    @Test
    void getAndSetToken() {
      resetRequestDto.setToken("newToken");
      assertEquals("newToken", resetRequestDto.getToken());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private ResetRequestDto anotherResetRequestDto;

    @BeforeEach
    void setUp() {
      anotherResetRequestDto = new ResetRequestDto();
      anotherResetRequestDto.setUsername("testUser");
      anotherResetRequestDto.setPassword("testPassword");
      anotherResetRequestDto.setToken("testToken");
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(resetRequestDto, anotherResetRequestDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherResetRequestDto.setUsername("newUser");
      assertNotEquals(resetRequestDto, anotherResetRequestDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(resetRequestDto.hashCode(), anotherResetRequestDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherResetRequestDto.setUsername("newUser");
      assertNotEquals(resetRequestDto.hashCode(), anotherResetRequestDto.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(resetRequestDto.toString());
  }
}
