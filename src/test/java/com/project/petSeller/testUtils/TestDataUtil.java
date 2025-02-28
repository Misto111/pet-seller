package com.project.petSeller.testUtils;

import com.project.petSeller.model.entity.BreedEntity;
import com.project.petSeller.model.entity.KindEntity;
import com.project.petSeller.model.entity.PetOfferEntity;
import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.model.enums.ColorEnum;
import com.project.petSeller.model.enums.GenderEnum;
import com.project.petSeller.repository.KindRepository;
import com.project.petSeller.repository.PetOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TestDataUtil {

    @Autowired
    private PetOfferRepository offerRepository;

    @Autowired
    private KindRepository kindRepository;

    public PetOfferEntity createTestOffer(UserEntity owner) {

        KindEntity kindEntity = kindRepository.save(new KindEntity()
                .setName("Test Kind")
                .setBreeds(List.of(
                        new BreedEntity().setName("Test Breed"),
                        new BreedEntity().setName("Test Breed1")
                )));

        PetOfferEntity offer = new PetOfferEntity()
                .setBreed(kindEntity.getBreeds().get(0))
                .setImageUrl("https://www.gooogle.com")
                .setPrice(BigDecimal.valueOf(1))
                .setYears(2)
                .setUuid(UUID.randomUUID())
                .setDescription("Test Description")
                .setColor(ColorEnum.WHITE)
                .setGender(GenderEnum.MALE)
                .setWeight(1.0)
                .setSeller(owner);

        return offerRepository.save(offer);
    }

    public void cleanUp() {

        offerRepository.deleteAll();
        kindRepository.deleteAll();

    }
}
