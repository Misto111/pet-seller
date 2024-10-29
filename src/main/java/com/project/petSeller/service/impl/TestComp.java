package com.project.petSeller.service.impl;

import com.project.petSeller.model.entity.KindEntity;
import com.project.petSeller.repository.KindRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestComp implements CommandLineRunner {

    private final KindRepository kindRepository;

    public TestComp(KindRepository kindRepository) {
        this.kindRepository = kindRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {


        List<KindEntity> kinds = kindRepository.findAll();


        kinds.forEach(k -> {


            k.getBreeds().forEach(System.out::println);

        });


    }
}
