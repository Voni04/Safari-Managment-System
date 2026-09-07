package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.entity.Booking;
import com.safari.safari_trip_system.entity.SafariPackage;
import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.repository.BookingRepository;
import com.safari.safari_trip_system.repository.SafariPackageRepository;
import com.safari.safari_trip_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SafariPackageRepository packageRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          SafariPackageRepository packageRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
    }

    // 1. Get all packages to display on the page
    public List<SafariPackage> getAllPackages() {
        return packageRepository.findAll();
    }

    // 2. Process a new booking
    public void createBooking(String username, Long packageId, LocalDate date) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SafariPackage safariPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSafariPackage(safariPackage);
        booking.setBookingDate(date);
        booking.setStatus("PENDING");

        bookingRepository.save(booking);
    }

    // 3. Find bookings for a specific user
    public List<Booking> getUserBookings(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUser(user);
    }

    // 4. Cancel a Booking (NEW FEATURE)
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Security check: Only allow deletion if NOT confirmed
        if ("CONFIRMED".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot delete a confirmed booking.");
        }

        bookingRepository.delete(booking);
    }
}

