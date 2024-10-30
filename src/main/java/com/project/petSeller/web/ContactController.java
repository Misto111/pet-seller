package com.project.petSeller.web;
import com.project.petSeller.model.dto.ContactDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/contact")
public class ContactController {

        @PostMapping
        public String submitContactForm(@RequestBody ContactDTO contactDTO) {
            // Тук можеш да добавиш логика за обработка на контактната информация
            // Например, да я запишеш в база данни или да изпратиш имейл

            return "Съобщението е успешно изпратено!";
        }
    }

