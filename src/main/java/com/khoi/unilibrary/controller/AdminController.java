package com.khoi.unilibrary.controller;


import com.khoi.unilibrary.entity.User;
import com.khoi.unilibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // Point to your admin dashboard page
    }

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", userService.findAllStudents());
        return "admin/students";
    }

    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/add-student";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute("user") User user) {
        user.setRole("STUDENT");
        userService.saveUser(user);
        return "redirect:/admin/students";
    }

    // Additional endpoints for editing or deleting students
}
