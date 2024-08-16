package com.khoi.unilibrary.controller;

import com.khoi.unilibrary.model.User;
import com.khoi.unilibrary.service.BookService;
import com.khoi.unilibrary.service.UserServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@RolesAllowed("ADMIN")
public class AdminController {

    private final BookService bookService;
    private final UserServiceImpl userService;

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "admin/books";
    }

    @GetMapping("/books/import")
    public String showImportBooksForm() {
        return "admin/import-books";
    }

    @PostMapping("/books/import")
    public String importBooks(@RequestParam("file") MultipartFile file, Model model) {
        try {
            File csvFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(csvFile);
            bookService.importBooksFromCsv(csvFile.getAbsolutePath());
        } catch (IOException e) {
            // Log the error and show a user-friendly message
            e.printStackTrace(); // Replace this with a logger in production
            model.addAttribute("error", "Failed to import books. Please try again.");
            return "admin/import-books";
        }
        return "redirect:/admin/books";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", userService.findAllStudents());
        return "admin/students";
    }

    @GetMapping("/students/create")
    public String showCreateStudentForm(Model model) {
        model.addAttribute("student", new User());
        return "admin/create-student";
    }

    @PostMapping("/students/create")
    public String createStudent(@ModelAttribute("student") User student) {
        userService.createStudent(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, Model model) {
        Optional<User> student = userService.findStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "admin/edit-student";
        } else {
            return "redirect:/admin/students";
        }
    }

    @PostMapping("/students/edit")
    public String editStudent(@ModelAttribute("student") User student) {
        userService.updateStudent(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        userService.deleteStudent(id);
        return "redirect:/admin/students";
    }

    @PostMapping("/students/reset-password")
    public String resetPassword(@RequestParam("id") Long id, @RequestParam("password") String password) {
        userService.resetPassword(id, password);
        return "redirect:/admin/students";
    }
}
