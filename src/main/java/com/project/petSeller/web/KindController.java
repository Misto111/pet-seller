package com.project.petSeller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KindController {

    @GetMapping("/kinds")
    public String allBrands() {

        return "kinds";

    }
}
