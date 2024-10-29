package com.project.petSeller.service.impl;

import com.project.petSeller.model.dto.AccessoryDetailDTO;
import com.project.petSeller.model.dto.AccessorySummaryDTO;
import com.project.petSeller.model.entity.AccessoryEntity;
import com.project.petSeller.repository.AccessoryRepository;
import com.project.petSeller.service.AccessoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryServiceImpl(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    @Override
    public Page<AccessorySummaryDTO> getAllAccessories(Pageable pageable) {
        return accessoryRepository
                .findAll(pageable)
                .map(AccessoryServiceImpl::mapAsSummary);
    }

    @Override
    public Optional<AccessoryDetailDTO> getAccessoryDetail(UUID accessoaryUUID) {
        return accessoryRepository
                .findByUuid(accessoaryUUID)
                .map(AccessoryServiceImpl::mapAsDetails);
    }

    private static AccessoryDetailDTO mapAsDetails(AccessoryEntity accessoryEntity) {
        return new AccessoryDetailDTO(
                accessoryEntity.getUuid().toString(),
                accessoryEntity.getDescription(),
                accessoryEntity.getAccessoryModel().getAccessoryBrand().getName(),
                accessoryEntity.getAccessoryModel().getName(),
                accessoryEntity.getPrice(),
                accessoryEntity.getImageUrl());

    }


    private static AccessorySummaryDTO mapAsSummary(AccessoryEntity accessoryEntity) {
        return new AccessorySummaryDTO(
                accessoryEntity.getUuid().toString(),
                accessoryEntity.getAccessoryModel().getName(),
                accessoryEntity.getAccessoryModel().getAccessoryBrand().getName(),
                accessoryEntity.getPrice(),
                accessoryEntity.getImageUrl());
    }
}
