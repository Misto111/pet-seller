package com.project.petSeller.web;

import com.project.petSeller.config.MailConfiguration;
import com.project.petSeller.model.dto.CreateOfferDTO;
import com.project.petSeller.model.dto.OfferDetailDTO;
import com.project.petSeller.model.enums.BreedCategoryEnum;
import com.project.petSeller.model.enums.ColorEnum;
import com.project.petSeller.model.enums.GenderEnum;
import com.project.petSeller.service.KindService;
import com.project.petSeller.service.OfferService;
import com.project.petSeller.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;
    private final KindService kindService;
    private final MailConfiguration mailConfiguration;

    public OfferController(OfferService offerService,
                           KindService kindService,
                           MailConfiguration mailConfiguration) {
        this.offerService = offerService;
        this.kindService = kindService;
        this.mailConfiguration = mailConfiguration;
    }



    @ModelAttribute("colors")
    public ColorEnum[] colors() {
        return ColorEnum.values();
    }


    @ModelAttribute("genders")
    public GenderEnum[] genders() {
      return GenderEnum.values();
    }
    @ModelAttribute("breeds")
    public BreedCategoryEnum[] breeds() {
        return BreedCategoryEnum.values();
    }


    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("createOfferDTO")) {
            model.addAttribute("createOfferDTO", CreateOfferDTO.empty());
        }


        model.addAttribute("kinds", kindService.getAllKinds());

        return "offer-add";
    }

    @PostMapping("/add")
    public String add(@Valid CreateOfferDTO createOfferDTO,
                      BindingResult bindingResult,
                      RedirectAttributes rAtt,
                      @AuthenticationPrincipal UserDetails seller) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("createOfferDTO", createOfferDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.createOfferDTO", bindingResult);
            return "redirect:/offer/add";
        }

       UUID newOfferUUID = offerService.createOffer(createOfferDTO, seller);

        return "redirect:/offer/" + newOfferUUID;
    }

    @GetMapping("/{uuid}")
    public String details(@PathVariable("uuid") UUID uuid, Model model,
                          @AuthenticationPrincipal UserDetails viewer) {

      OfferDetailDTO offerDetailDTO = offerService
                .getOfferDetail(uuid, viewer)
                .orElseThrow(() -> new ObjectNotFoundException("Offer with uuid " + uuid + " was not found!"));

      model.addAttribute("offer", offerDetailDTO);

        return "details";
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#uuid, #principal.username)")
  @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") UUID uuid,
                         @AuthenticationPrincipal UserDetails principal) {

        offerService.deleteOffer(uuid);


      return "redirect:/offers/all";

  }

}

