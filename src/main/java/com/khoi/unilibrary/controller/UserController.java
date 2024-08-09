package com.khoi.unilibrary.controller;

import com.khoi.unilibrary.entity.User;
import com.khoi.unilibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllStudents();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }
}
