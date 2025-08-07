package com.blitz.resumescreener.controller;

import com.blitz.resumescreener.model.Role;
import com.blitz.resumescreener.model.User;
import com.blitz.resumescreener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // --- THIS IS THE METHOD TO UPDATE ---
    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(name = "role", required = false) Role role, Model model) {
        // If no role is selected from the header link, redirect to the homepage
        if (role == null) {
            return "redirect:/";
        }
        
        User user = new User();
        user.setRole(role); // Set the role from the URL parameter
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login?registered";
    }
}