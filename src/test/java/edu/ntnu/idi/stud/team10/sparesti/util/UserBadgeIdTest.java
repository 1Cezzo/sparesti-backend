package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserBadgeIdTest {

  @Test
  public void testEquals() {
    UserBadgeId id1 = new UserBadgeId();
    id1.setUser(1L);
    id1.setBadge(1L);

    UserBadgeId id2 = new UserBadgeId();
    id2.setUser(1L);
    id2.setBadge(1L);

    Assertions.assertTrue(id1.equals(id2));
  }

  @Test
  public void testHashCode() {
    UserBadgeId id1 = new UserBadgeId();
    id1.setUser(1L);
    id1.setBadge(1L);

    UserBadgeId id2 = new UserBadgeId();
    id2.setUser(1L);
    id2.setBadge(1L);

    Assertions.assertEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  public void testToString() {
    UserBadgeId id = new UserBadgeId();
    id.setUser(1L);
    id.setBadge(1L);

    Assertions.assertEquals("UserBadgeId(user=1, badge=1)", id.toString());
  }

  @Test
  public void testSetUser() {
    UserBadgeId id = new UserBadgeId();
    id.setUser(1L);

    Assertions.assertEquals(1L, id.getUser());
  }

  @Test
  public void testSetBadge() {
    UserBadgeId id = new UserBadgeId();
    id.setBadge(1L);

    Assertions.assertEquals(1L, id.getBadge());
  }

  @Test
  public void testGetUser() {
    UserBadgeId id = new UserBadgeId();
    id.setUser(1L);

    Assertions.assertEquals(1L, id.getUser());
  }

  @Test
  public void testGetBadge() {
    UserBadgeId id = new UserBadgeId();
    id.setBadge(1L);

    Assertions.assertEquals(1L, id.getBadge());
  }

  @Test
  public void testCanEqual() {
    UserBadgeId id1 = new UserBadgeId();
    id1.setUser(1L);
    id1.setBadge(1L);

    UserBadgeId id2 = new UserBadgeId();
    id2.setUser(1L);
    id2.setBadge(1L);

    Assertions.assertTrue(id1.canEqual(id2));
  }
}
