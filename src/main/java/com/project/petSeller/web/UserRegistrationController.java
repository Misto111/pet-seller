package com.project.petSeller.web;

import com.project.petSeller.model.dto.UserRegistrationDTO;
import com.project.petSeller.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequestMapping("/users")
@Controller
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(
            UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {


        if (!model.containsAttribute("userRegistrationDTO")) {
            model.addAttribute("userRegistrationDTO", UserRegistrationDTO.emptyUserRegistrationDTO());
        }

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, RedirectAttributes rAtt) {


        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);
            return "redirect:register";
        }

        userService.registerUser(userRegistrationDTO);

        return "redirect:login";

    }
}
