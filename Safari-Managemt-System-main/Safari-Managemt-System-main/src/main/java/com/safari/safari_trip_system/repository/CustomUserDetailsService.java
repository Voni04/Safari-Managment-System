package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Find the user in our DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Create a "Authority" based on their role (e.g., "ROLE_ADMIN")
        // Spring Security expects roles to start with "ROLE_" usually, but we can handle that here or in config.
        // For simplicity, we just pass the role name string.
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // 3. Return the Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}