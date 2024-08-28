package com.khoi.unilibrary.controller;

import com.khoi.unilibrary.model.User;
import com.khoi.unilibrary.service.UserServiceImpl;
import com.khoi.unilibrary.token.PasswordResetToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordController(UserServiceImpl userServiceImpl, PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = userServiceImpl.getPasswordResetToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("error", "Invalid or expired token.");
            return "reset-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String newPassword, Model model) {
        PasswordResetToken resetToken = userServiceImpl.getPasswordResetToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("error", "Invalid or expired token.");
            return "reset-password";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userServiceImpl.saveUser(user);

        model.addAttribute("message", "Password successfully reset.");
        return "login";
    }
}
