package com.blitz.resumescreener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployerController {

    @GetMapping("/employer-dashboard")
    public String showEmployerDashboard() {
        // This tells Spring to look for and return "employer-dashboard.html"
        return "employer-dashboard";
    }
}