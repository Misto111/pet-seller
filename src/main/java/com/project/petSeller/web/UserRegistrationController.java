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

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    // GET метод за зареждане на формата за регистрация
    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegistrationDTO")) {
            model.addAttribute("userRegistrationDTO", UserRegistrationDTO.emptyUserRegistrationDTO());
        }
        return "register";
    }


    // POST метод за обработка на формата при изпращане
    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, RedirectAttributes rAtt) {

        // Ако има грешки в данните, връщаме формата с грешки
        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);
            return "redirect:register"; // Пренасочваме обратно към формата
        }

        // Ако няма грешки, създаваме потребителя
        userService.registerUser(userRegistrationDTO); // Използваме метод registerUser

        // Пренасочваме към страницата за вход
        return "redirect:login";
    }
}