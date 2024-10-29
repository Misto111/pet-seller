package com.project.petSeller.model.dto;

import java.util.List;

public record KindDTO(String name, List<BreedDTO> breeds) {
}
