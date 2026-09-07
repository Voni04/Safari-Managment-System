package com.safari.safari_trip_system.controller;

import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    // 1. Show My Profile (For Tourist)
    @GetMapping("/profile")
    public String showMyProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    // 2. Process Profile Update
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User user) {
        userService.updateUserProfile(user);
        return "redirect:/profile?success";
    }

    // REMOVED: showAllTourists (This method MUST NOT be here)
}