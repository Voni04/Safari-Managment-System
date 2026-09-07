package com.safari.safari_trip_system.config;

import com.safari.safari_trip_system.entity.Role;
import com.safari.safari_trip_system.entity.SafariPackage;
import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.entity.Vehicle;
import com.safari.safari_trip_system.repository.SafariPackageRepository;
import com.safari.safari_trip_system.repository.UserRepository;
import com.safari.safari_trip_system.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SafariPackageRepository packageRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(SafariPackageRepository packageRepository,
                      UserRepository userRepository,
                      VehicleRepository vehicleRepository,
                      PasswordEncoder passwordEncoder) {
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed Packages (Check if empty)
        if (packageRepository.count() == 0) {
            System.out.println("--- Seeding Safari Packages ---");
            SafariPackage p1 = new SafariPackage(null, "Morning Jeep Safari", "A 4-hour thrilling ride.", 50.00);
            SafariPackage p2 = new SafariPackage(null, "Full Day Adventure", "Experience the wild from dawn to dusk.", 120.00);
            SafariPackage p3 = new SafariPackage(null, "Night Camping", "Stay overnight in a secure campsite.", 200.00);
            packageRepository.saveAll(Arrays.asList(p1, p2, p3));
        }

        // 2. Seed Admin (Check if 'admin' exists)
        if (userRepository.findByUsername("admin").isEmpty()) {
            System.out.println("--- Seeding Admin User ---");
            User admin = new User();
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setUsername("admin");
            admin.setEmail("admin@safari.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        // 3. Seed Guide (Check if 'guide1' exists)
        if (userRepository.findByUsername("guide1").isEmpty()) {
            System.out.println("--- Seeding Guide User ---");
            User guide = new User();
            guide.setFirstName("Ranger");
            guide.setLastName("Rick");
            guide.setUsername("guide1");
            guide.setEmail("guide1@safari.com");
            guide.setPassword(passwordEncoder.encode("guide123"));
            guide.setRole(Role.GUIDE);
            userRepository.save(guide);
        }

        // 4. Seed Vehicles (Check if empty)
        if (vehicleRepository.count() == 0) {
            System.out.println("--- Seeding Vehicles ---");
            Vehicle v1 = new Vehicle(null, "WP-CAB-1234", "Toyota Hilux", 6);
            Vehicle v2 = new Vehicle(null, "WP-JA-9988", "Land Rover Defender", 6);
            vehicleRepository.saveAll(Arrays.asList(v1, v2));
        }
    }
}
