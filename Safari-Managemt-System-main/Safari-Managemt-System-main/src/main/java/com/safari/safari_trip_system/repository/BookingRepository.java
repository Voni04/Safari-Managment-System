package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.Booking;
import com.safari.safari_trip_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Find all bookings made by a specific user
    List<Booking> findByUser(User user);
    List<Booking> findByStatus(String status);
}
