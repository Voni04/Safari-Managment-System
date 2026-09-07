package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.Role; // Import Role
import com.safari.safari_trip_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // NEW: Find all users with a specific role (e.g., all GUIDES)
    List<User> findByRole(Role role);
}
