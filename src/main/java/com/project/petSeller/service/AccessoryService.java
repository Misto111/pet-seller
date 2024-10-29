package com.project.petSeller.service;

import com.project.petSeller.model.dto.AccessoryDetailDTO;
import com.project.petSeller.model.dto.AccessorySummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AccessoryService {

    Page<AccessorySummaryDTO> getAllAccessories(Pageable pageable);

    Optional<AccessoryDetailDTO> getAccessoryDetail(UUID offerUUID);

}
