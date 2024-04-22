package edu.ntnu.idi.stud.team10.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {}
