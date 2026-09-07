package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.entity.Booking;
import com.safari.safari_trip_system.entity.Role;
import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.entity.Vehicle;
import com.safari.safari_trip_system.repository.BookingRepository;
import com.safari.safari_trip_system.repository.UserRepository;
import com.safari.safari_trip_system.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public AdminService(BookingRepository bookingRepository,
                        UserRepository userRepository,
                        VehicleRepository vehicleRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    // --- Operational Methods ---

    public List<Booking> getConfirmedBookings() {
        return bookingRepository.findByStatus("CONFIRMED");
    }

    public List<User> getAllGuides() {
        return userRepository.findByRole(Role.GUIDE);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void assignResources(Long bookingId, Long guideId, Long vehicleId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User guide = userRepository.findById(guideId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        booking.setGuide(guide);
        booking.setVehicle(vehicle);

        bookingRepository.save(booking);
    }

    // --- REPORT / ANALYTICS METHODS (NEW) ---

    // 1. Calculate Total Revenue from CONFIRMED bookings
    public Double calculateTotalRevenue() {
        List<Booking> paidBookings = bookingRepository.findByStatus("CONFIRMED");
        return paidBookings.stream()
                .mapToDouble(b -> b.getSafariPackage().getPrice())
                .sum();
    }

    // 2. Count Confirmed Bookings
    public long countConfirmedBookings() {
        return bookingRepository.findByStatus("CONFIRMED").size();
    }

    // 3. Count Pending Bookings
    public long countPendingBookings() {
        return bookingRepository.findByStatus("PENDING").size();
    }
}