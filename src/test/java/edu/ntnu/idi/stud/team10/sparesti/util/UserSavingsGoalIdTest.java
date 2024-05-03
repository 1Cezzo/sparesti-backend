package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserSavingsGoalIdTest {

  @Test
  public void testEquals() {
    UserSavingsGoalId id1 = new UserSavingsGoalId();
    id1.setUser(1L);
    id1.setSavingsGoal(1L);

    UserSavingsGoalId id2 = new UserSavingsGoalId();
    id2.setUser(1L);
    id2.setSavingsGoal(1L);

    Assertions.assertTrue(id1.equals(id2));
  }

  @Test
  public void testHashCode() {
    UserSavingsGoalId id1 = new UserSavingsGoalId();
    id1.setUser(1L);
    id1.setSavingsGoal(1L);

    UserSavingsGoalId id2 = new UserSavingsGoalId();
    id2.setUser(1L);
    id2.setSavingsGoal(1L);

    Assertions.assertEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  public void testToString() {
    UserSavingsGoalId id = new UserSavingsGoalId();
    id.setUser(1L);
    id.setSavingsGoal(1L);

    Assertions.assertEquals("UserSavingsGoalId(user=1, savingsGoal=1)", id.toString());
  }

  @Test
  public void testSetUser() {
    UserSavingsGoalId id = new UserSavingsGoalId();
    id.setUser(1L);

    Assertions.assertEquals(1L, id.getUser());
  }

  @Test
  public void testSetSavingsGoal() {
    UserSavingsGoalId id = new UserSavingsGoalId();
    id.setSavingsGoal(1L);

    Assertions.assertEquals(1L, id.getSavingsGoal());
  }

  @Test
  public void testGetUser() {
    UserSavingsGoalId id = new UserSavingsGoalId();
    id.setUser(1L);

    Assertions.assertEquals(1L, id.getUser());
  }

  @Test
  public void testGetSavingsGoal() {
    UserSavingsGoalId id = new UserSavingsGoalId();
    id.setSavingsGoal(1L);

    Assertions.assertEquals(1L, id.getSavingsGoal());
  }

  @Test
  public void testCanEqual() {
    UserSavingsGoalId id1 = new UserSavingsGoalId();
    id1.setUser(1L);
    id1.setSavingsGoal(1L);

    UserSavingsGoalId id2 = new UserSavingsGoalId();
    id2.setUser(1L);
    id2.setSavingsGoal(1L);

    Assertions.assertTrue(id1.canEqual(id2));
  }
}
