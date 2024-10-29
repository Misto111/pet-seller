package com.project.petSeller.service.impl;

import com.project.petSeller.model.dto.CreateOfferDTO;
import com.project.petSeller.model.dto.OfferDetailDTO;
import com.project.petSeller.model.dto.OfferSummaryDTO;
import com.project.petSeller.model.entity.*;
import com.project.petSeller.model.enums.UserRoleEnum;
import com.project.petSeller.repository.BreedRepository;
import com.project.petSeller.repository.OfferRepository;
import com.project.petSeller.repository.UserRepository;
import com.project.petSeller.service.MonitoringService;
import com.project.petSeller.service.OfferService;
import com.project.petSeller.service.aop.WarnIfExecutionExceeds;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final BreedRepository breedRepository;
    private final MonitoringService monitoringService;
    private final UserRepository userRepository;


    public OfferServiceImpl(OfferRepository offerRepository,
                            BreedRepository breedRepository,
                            MonitoringService monitoringService,
                            UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.breedRepository = breedRepository;
        this.monitoringService = monitoringService;
        this.userRepository = userRepository;
    }

    @Override
    public UUID createOffer(CreateOfferDTO createOfferDTO, UserDetails seller) {


        // когато не сме имплементирали един метод можем да хвърляме exception
        OfferEntity newOffer = map(createOfferDTO);
        BreedEntity breedEntity = breedRepository.findById(createOfferDTO.breedId()).orElseThrow(() ->
                new IllegalArgumentException("Breed with id " + createOfferDTO.breedId() + " not found!"));

        UserEntity sellerEntity = userRepository.findByEmail(seller.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with email " + seller.getUsername() + " not found!"));

        newOffer.setBreed(breedEntity);
        newOffer.setSeller(sellerEntity);

        newOffer = offerRepository.save(newOffer);
        return newOffer.getUuid();
    }

    @WarnIfExecutionExceeds(
            timeInMillis = 1000L
    )

    @Override
    public Page<OfferSummaryDTO> getAllOffers(Pageable pageable) {

        System.out.println("IN GET ALL OFFERS");

       return  offerRepository
                .findAll(pageable)
                .map(OfferServiceImpl::mapAsSummary);

       }
    @WarnIfExecutionExceeds(
            timeInMillis = 500L
    )


    @Override
   public Optional<OfferDetailDTO> getOfferDetail(UUID offerUUID, UserDetails viewer) {
        return offerRepository
                .findByUuid(offerUUID)
                .map(o -> this.mapAsDetails(o, viewer));


    }

    @Override
    @Transactional
    public void deleteOffer(UUID offerUUID) {
         offerRepository.deleteByUuid(offerUUID);
    }

    private  OfferDetailDTO mapAsDetails(OfferEntity offerEntity, UserDetails viewer) {
        return new OfferDetailDTO(
                offerEntity.getUuid().toString(),
                offerEntity.getDescription(),
                offerEntity.getBreed().getKind().getName(),
                offerEntity.getBreed().getName(),
                offerEntity.getYears(),
                offerEntity.getWeight(),
                offerEntity.getPrice(),
                offerEntity.getColor(),
                offerEntity.getGender(),
                offerEntity.getImageUrl(),
                offerEntity.getSeller().getFirstName(),
                isOwner(offerEntity, viewer != null? viewer.getUsername() : null));

    }


    @Override
    public boolean isOwner(UUID uuid, String userName) {


        return isOwner(
                offerRepository.findByUuid(uuid).orElse(null),
                userName
        );


    }

    private boolean isOwner(OfferEntity offerEntity, String userName) {

        if (offerEntity == null || userName == null) {
            return false;
        }



     UserEntity viewerEntity =  userRepository
             .findByEmail(userName)
             .orElseThrow(() -> new IllegalArgumentException("Unknown user..."));

        if (isAdmin(viewerEntity)) {
            return true;
        }
        return Objects.equals(offerEntity.getSeller().getId(), viewerEntity.getId());

    }

    private boolean isAdmin(UserEntity userEntity) {

        return userEntity
                .getRoles()
                .stream()
                .map(UserRoleEntity::getRole)
                .anyMatch(r -> UserRoleEnum.ADMIN == r);
    }


    private static OfferSummaryDTO mapAsSummary(OfferEntity offerEntity) {
        return new OfferSummaryDTO(
                offerEntity.getUuid().toString(),
                offerEntity.getBreed().getKind().getName(),
                offerEntity.getBreed().getName(),
                offerEntity.getYears(),
                offerEntity.getWeight(),
                offerEntity.getPrice(),
                offerEntity.getColor(),
                offerEntity.getGender(),
                offerEntity.getImageUrl());

    }


    private static OfferEntity map(CreateOfferDTO createOfferDTO) {
        return new OfferEntity()
                .setUuid(UUID.randomUUID())
                .setDescription(createOfferDTO.description())
                .setColor(createOfferDTO.color())
                .setGender(createOfferDTO.gender())
                .setImageUrl(createOfferDTO.imageUrl())
                .setWeight(createOfferDTO.weight())
                .setPrice(BigDecimal.valueOf(createOfferDTO.price()))
                .setYears(createOfferDTO.years());
    }
}
