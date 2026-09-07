package com.safari.safari_trip_system.controller;

import com.safari.safari_trip_system.dto.UserRegistrationDto;
import com.safari.safari_trip_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Show the Home Page
    @GetMapping("/")
    public String home() {
        return "index"; // We will create index.html next
    }

    // Show Login Page
    @GetMapping("/login")
    public String login() {
        return "login"; // We will create login.html next
    }

    // Show Registration Page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserRegistrationDto user = new UserRegistrationDto();
        model.addAttribute("user", user);
        return "register"; // We will create register.html next
    }

    // Process Registration Form
    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                                      BindingResult result,
                                      Model model) {
        // If there are validation errors (e.g. empty email), stay on page
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        try {
            userService.saveUser(userDto);
        } catch (Exception e) {
            // Handle duplicate email/username error
            result.rejectValue("email", null, "There is already an account registered with that email or username");
            return "register";
        }

        return "redirect:/register?success";
    }
}