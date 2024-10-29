package com.project.petSeller.service.impl;

import com.project.petSeller.model.dto.BreedDTO;
import com.project.petSeller.model.dto.KindDTO;
import com.project.petSeller.repository.KindRepository;
import com.project.petSeller.service.KindService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KindServiceImpl implements KindService {


    private final KindRepository kindRepository;

    public KindServiceImpl(KindRepository kindRepository) {
        this.kindRepository = kindRepository;
        ;
    }

    @Override
    public List<KindDTO> getAllKinds() {    //идеята е да взема всички видове и да ги парсна в темплийта


        return kindRepository.findAll().stream()
                .map(kind -> new KindDTO(
                        kind.getName(),
                        kind.getBreeds().stream()
                                .map(breed-> new BreedDTO(breed.getId(), breed.getName()))
                                .sorted(Comparator.comparing(BreedDTO::name))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator.comparing(KindDTO::name))
                .collect(Collectors.toList());
    }
}
