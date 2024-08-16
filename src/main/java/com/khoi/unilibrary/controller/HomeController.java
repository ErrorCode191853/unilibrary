package com.khoi.unilibrary.controller;

import com.khoi.unilibrary.model.User;
import com.khoi.unilibrary.model.Activity;
import com.khoi.unilibrary.model.Notification;
import com.khoi.unilibrary.service.UserServiceImpl;
import com.khoi.unilibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value = "/home", method = RequestMethod.GET)
@RequiredArgsConstructor
public class HomeController {

    private final UserServiceImpl userService;
    private final BookService bookService;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        model.addAttribute("user", user);

        switch (user.get().getRole()) {
            case "ADMIN":
                model.addAttribute("totalBooks", bookService.countBooks());
                model.addAttribute("totalStudents", userService.countStudents());
                model.addAttribute("totalStaff", userService.countStaff());
                model.addAttribute("recentActivities", getRecentActivities());
                return "admin-home";
            case "STUDENT":
                model.addAttribute("borrowedBooks", bookService.findBorrowedBooksByUser(user.get().getUserId()));
                model.addAttribute("notifications", getNotifications(user.orElse(null)));
                return "student-home";
            default:
                // Log unknown role or handle it accordingly
                return "redirect:/login";
        }
    }

    // Placeholder methods for recent activities and notifications
    private List<Activity> getRecentActivities() {
        // Implement logic to fetch recent activities
        return new ArrayList<>();
    }

    private List<Notification> getNotifications(User user) {
        // Implement logic to fetch notifications for the user
        return new ArrayList<>();
    }
}
