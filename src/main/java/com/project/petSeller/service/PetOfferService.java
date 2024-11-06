package com.project.petSeller.service;

import com.project.petSeller.model.dto.CreateOfferDTO;
import com.project.petSeller.model.dto.PetOfferDetailDTO;
import com.project.petSeller.model.dto.PetOfferSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface PetOfferService {

    UUID createOffer(CreateOfferDTO createOfferDTO, UserDetails seller);

    Page<PetOfferSummaryDTO> getAllOffers(Pageable pageable);

    Optional<PetOfferDetailDTO> getOfferDetail(UUID offerUUID, UserDetails viewer);

    void deleteOffer(UUID offerUUID);

    boolean isOwner(UUID uuid, String userName);
}
