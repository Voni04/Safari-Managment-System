package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.dto.UserRegistrationDto;
import com.safari.safari_trip_system.entity.Role;
import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserRegistrationDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(Role.TOURIST);

        userRepository.save(user);
    }

    // --- NEW METHODS FOR PROFILE MANAGEMENT ---

    // 1. Get user by username (Helper for Controller)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 2. Update User Profile (Non-Password fields)
    public void updateUserProfile(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Only update specific fields
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());

        // We do NOT update password, role, or username here for security
        userRepository.save(existingUser);
    }

    // 3. Get all Tourists (For Admin View)
    public List<User> getAllTourists() {
        return userRepository.findByRole(Role.TOURIST);
    }
}