package com.project.petSeller.repository;

import com.project.petSeller.model.entity.PetOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetOfferRepository extends JpaRepository<PetOfferEntity, Long> {

    Optional<PetOfferEntity> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
