package edu.ntnu.idi.stud.team10.sparesti.util;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserBadgeId implements Serializable {
  private Long user;
  private Long badge;
}
