package com.project.petSeller.model.entity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<AccessoryEntity> accessories = new ArrayList<>();
    private List<OfferEntity> offerEntities = new ArrayList<>();

    public void addOffer(OfferEntity offerEntity) {
        offerEntities.add(offerEntity);
    }


    public void addAccessory(AccessoryEntity accessory) {
        accessories.add(accessory);
    }

    public void removeOffer(OfferEntity offerEntity) {
        offerEntities.remove(offerEntity);
    }

    public void removeAccessory(AccessoryEntity accessory) {
        accessories.remove(accessory);
    }

    public List<OfferEntity> getOffers() {
        return offerEntities;
    }

    public List<AccessoryEntity> getAccessories() {
        return accessories;
    }

    public void clearOffer() {
        offerEntities.clear();
    }

    public void clear() {
        accessories.clear();
    }


    public BigDecimal getTotalPrice() {
        BigDecimal totalAccessoryPrice = accessories.stream()
                .map(AccessoryEntity::getPrice) // Предполагам, че getPrice() връща BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOfferPrice = offerEntities.stream()
                .map(OfferEntity::getPrice) // Предполагам, че getPrice() връща BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalAccessoryPrice.add(totalOfferPrice); // Събиране на цените
    }
}
