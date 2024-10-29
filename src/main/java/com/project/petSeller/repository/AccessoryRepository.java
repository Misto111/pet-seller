package com.project.petSeller.repository;

import com.project.petSeller.model.entity.AccessoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface AccessoryRepository extends JpaRepository<AccessoryEntity, Long> {

    Optional<AccessoryEntity> findByUuid(UUID uuid);


}
