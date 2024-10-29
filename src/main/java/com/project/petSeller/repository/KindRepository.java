package com.project.petSeller.repository;

import com.project.petSeller.model.entity.KindEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindRepository extends JpaRepository<KindEntity, Long> {

    @EntityGraph(
            value = "kindWithModels",
            attributePaths = "breeds")
    @Query("SELECT k FROM KindEntity k")
     List<KindEntity> getAllKinds();
}
