package com.project.petSeller.model.dto;

import com.project.petSeller.model.enums.ColorEnum;
import com.project.petSeller.model.enums.GenderEnum;
import jakarta.validation.constraints.*;

public record CreateOfferDTO(@NotEmpty @Size(min = 5, max = 512)
                             String description,
                             @Positive
                             @NotNull
                             Long breedId,
                             @NotNull
                             ColorEnum color,
                             @NotNull
                             GenderEnum gender,
                             @NotEmpty
                             String imageUrl,
                             @Positive
                             @NotNull
                             Double weight,
                             @Positive
                             @NotNull
                             Integer price,
                             @NotNull(message = "Years must be provided")
                             Integer years) {

    public static CreateOfferDTO empty() {
        return new CreateOfferDTO(null, null, null, null, null, null, null, null);
    }

}



