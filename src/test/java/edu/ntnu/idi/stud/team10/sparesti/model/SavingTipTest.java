package edu.ntnu.idi.stud.team10.sparesti.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SavingTipTest {
  private SavingTip savingTip;

  @BeforeEach
  public void setUp() {
    savingTip = new SavingTip();
    savingTip.setId(1L);
    savingTip.setMessage("Test Saving Tip");
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      savingTip.setId(2L);
      assertEquals(2L, savingTip.getId());
    }

    @Test
    void getAndSetMessage() {
      savingTip.setMessage("Test Saving Tip 2");
      assertEquals("Test Saving Tip 2", savingTip.getMessage());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private SavingTip anotherSavingTip;

    @BeforeEach
    void setUp() {
      anotherSavingTip = new SavingTip();
      anotherSavingTip.setId(1L);
      anotherSavingTip.setMessage("Test Saving Tip");
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(savingTip, anotherSavingTip);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherSavingTip.setMessage("Test Saving Tip 2");
      assertNotEquals(savingTip, anotherSavingTip);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(savingTip.hashCode(), anotherSavingTip.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherSavingTip.setMessage("Test Saving Tip 2");
      assertNotEquals(savingTip.hashCode(), anotherSavingTip.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(savingTip.toString());
  }
}
