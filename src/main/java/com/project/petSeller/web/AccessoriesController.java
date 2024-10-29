package com.project.petSeller.web;

import com.project.petSeller.model.dto.AccessoryDetailDTO;
import com.project.petSeller.model.dto.AccessorySummaryDTO;
import com.project.petSeller.service.AccessoryService;
import com.project.petSeller.service.exception.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/accessories")
public class AccessoriesController {

    private final AccessoryService accessoryService;

    public AccessoriesController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }
    @GetMapping("/{uuid}")
    public String details(@PathVariable("uuid") UUID uuid, Model model) {

        AccessoryDetailDTO accessoryDetailDTO = accessoryService
                .getAccessoryDetail(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Accessory with uuid " + uuid + " was not found!"));

        model.addAttribute("accessories", accessoryDetailDTO);

        return "accessories-details";
    }

    @GetMapping("/all")
    public String all(Model model,
                      @PageableDefault(
                              size = 3,
                              sort = "uuid"
                      )
                      Pageable pageable) {
;
       Page<AccessorySummaryDTO> allAccessories = accessoryService.getAllAccessories(pageable);

       model.addAttribute("accessories", allAccessories);

        return "accessories";
    }

}
