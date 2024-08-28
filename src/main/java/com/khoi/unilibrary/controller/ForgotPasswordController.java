package com.khoi.unilibrary.controller;

import com.khoi.unilibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

    private final UserService userService;

    @Autowired
    public ForgotPasswordController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        String token = userService.createPasswordResetToken(email);  // Generate reset token

        if (token != null) {
            // Send email with reset link
            String resetUrl = "http://localhost:8080/reset-password?token=" + token;
            userService.sendPasswordResetEmail(email, resetUrl);

            model.addAttribute("message", "Password reset link has been sent to your email.");
        } else {
            model.addAttribute("error", "Email address not found.");
        }

        return "forgot-password";
    }
}
