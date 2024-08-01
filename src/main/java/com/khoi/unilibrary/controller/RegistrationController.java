package com.khoi.unilibrary.controller;

import com.khoi.unilibrary.dto.RegistrationForm;
import com.khoi.unilibrary.entity.User;
import com.khoi.unilibrary.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registrationForm") @Valid RegistrationForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("passwordMismatch", "Passwords do not match");
            return "register";
        }

        if (userService.findByUsername(form.getUsername()).isPresent()) {
            model.addAttribute("userExists", "Username already exists");
            return "register";
        }

        userService.saveUser(form);
        return "redirect:/login?registered";
    }
}

