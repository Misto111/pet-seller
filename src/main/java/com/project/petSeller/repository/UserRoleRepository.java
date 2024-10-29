package com.project.petSeller.repository;


import com.project.petSeller.model.entity.UserRoleEntity;
import com.project.petSeller.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

  List<UserRoleEntity> findAllByRoleIn(List<UserRoleEnum> roles);

}
