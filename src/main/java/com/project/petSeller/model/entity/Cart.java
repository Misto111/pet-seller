package com.project.petSeller.model.entity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<AccessoryEntity> accessories = new ArrayList<>();

    public void addAccessory(AccessoryEntity accessory) {
        accessories.add(accessory);
    }

    public void removeAccessory(AccessoryEntity accessory) {
        accessories.remove(accessory);
    }

    public List<AccessoryEntity> getAccessories() {
        return accessories;
    }

    public void clear() {
        accessories.clear();
    }

    // Промяна на метода getTotalPrice да връща BigDecimal
    public BigDecimal getTotalPrice() {
        return accessories.stream()
                .map(AccessoryEntity::getPrice)  // Предполагам, че getPrice() връща BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
