package com.project.petSeller.web;

import com.project.petSeller.model.entity.AccessoryEntity;
import com.project.petSeller.model.entity.Cart;
import com.project.petSeller.model.entity.PetOfferEntity;
import com.project.petSeller.repository.AccessoryRepository;
import com.project.petSeller.repository.PetOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private PetOfferRepository offerRepository;

    // Get or create a cart in the session
    public Cart getCart() {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("uuid") UUID accessoryUuid, Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("errorMessage", "Please log in to add items to your cart.");
            return "redirect:/users/login"; // Redirect to login page
        }

        // Check if UUID is valid
        if (accessoryUuid == null) {
            model.addAttribute("errorMessage", "Accessory not found.");
            model.addAttribute("cart", getCart()); // Ensure cart is added to the model
            return "cart-view"; // Return the cart view
        }

        // Retrieve the accessory by UUID
        AccessoryEntity accessory = accessoryRepository.findByUuid(accessoryUuid).orElse(null);
        if (accessory != null) {
            getCart().addAccessory(accessory);
        } else {
            model.addAttribute("errorMessage", "Accessory not found.");
        }

        model.addAttribute("cart", getCart()); // Ensure cart is always in the model
        return "cart-view"; // Return cart view
    }

    @PostMapping("/cart/addOffer")
    public String addOfferToCart(@RequestParam("uuid") UUID offerUuid, Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("errorMessage", "Please log in to add items to your cart.");
            return "redirect:/users/login"; // Redirect to login page
        }

        // Check if UUID is valid
        if (offerUuid == null) {
            model.addAttribute("errorMessage", "Offer not found.");
            model.addAttribute("cart", getCart());
            return "cart-view"; // Return the cart view
        }

        // Retrieve the offer by UUID
        PetOfferEntity offer = offerRepository.findByUuid(offerUuid).orElse(null);
        if (offer != null) {
            getCart().addOffer(offer);
        } else {
            model.addAttribute("errorMessage", "Offer not found.");
        }

        model.addAttribute("cart", getCart());
        return "cart-view"; // Return cart view
    }


    @GetMapping("/cart")
    public String cartView(Model model) {
        model.addAttribute("cart", getCart()); // Добави количката в модела
        return "cart-view"; // Върни шаблона
    }
    }
