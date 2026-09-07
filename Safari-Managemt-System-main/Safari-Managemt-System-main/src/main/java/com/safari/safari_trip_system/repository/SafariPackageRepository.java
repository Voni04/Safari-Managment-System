package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.SafariPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SafariPackageRepository extends JpaRepository<SafariPackage, Long> {
    // Basic CRUD operations are built-in
}