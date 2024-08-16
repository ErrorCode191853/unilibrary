package com.khoi.unilibrary;

import com.khoi.unilibrary.model.User;
import com.khoi.unilibrary.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final UserServiceImpl userService;

    @Autowired
    public AppStartupRunner(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> students = userService.findAllStudents();
        System.out.println("Number of students: " + students.size());
    }
}
