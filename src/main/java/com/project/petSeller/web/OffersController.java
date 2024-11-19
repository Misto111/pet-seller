package com.project.petSeller.web;

import com.project.petSeller.model.dto.PetOfferSummaryDTO;
import com.project.petSeller.service.PetOfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OffersController {

    private final PetOfferService offerService;

    public OffersController(PetOfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/all")
    public String all(Model model,
                      @PageableDefault(
                              size = 3,
                              sort = "uuid"
                      )
                      Pageable pageable) {

       Page<PetOfferSummaryDTO> allOffers = offerService.getAllOffers(pageable);

       model.addAttribute("offers", allOffers);

        return "ads";
    }
}
