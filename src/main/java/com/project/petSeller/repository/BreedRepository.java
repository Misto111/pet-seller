package com.project.petSeller.repository;

import com.project.petSeller.model.dto.BreedDTO;
import com.project.petSeller.model.entity.BreedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<BreedEntity, Long> {
    List<BreedDTO> findAllByKindId(Long id);
}
