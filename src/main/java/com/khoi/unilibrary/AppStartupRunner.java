package com.khoi.unilibrary;

import com.khoi.unilibrary.entity.User;
import com.khoi.unilibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public AppStartupRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> students = userService.findAllStudents();
        System.out.println("Number of students: " + students.size());
    }
}
