package com.khoi.unilibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value = "/account")
public class LoginController {

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView loadForm() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }
@GetMapping("/login")
public String login() {
    return "login"; // Returns login.html
}
    @GetMapping("/home")
    public String home() {
        return "home"; // Returns home.html after successful login
    }
}

