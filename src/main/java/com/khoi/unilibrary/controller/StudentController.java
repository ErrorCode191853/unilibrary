package com.khoi.unilibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/dashboard")
    public String studentDashboard() {
        return "student/dashboard"; // Point to your student dashboard page
    }

    // Add more student-specific mappings here
}

