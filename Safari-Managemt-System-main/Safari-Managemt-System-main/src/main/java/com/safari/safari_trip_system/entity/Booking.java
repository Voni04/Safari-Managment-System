package com.safari.safari_trip_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED

    // --- Relationships ---

    // The Tourist who made the booking
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The Package selected
    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private SafariPackage safariPackage;

    // --- NEW FIELDS FOR ADMIN SCHEDULING ---

    // The Guide assigned to this trip (Can be null initially)
    @ManyToOne
    @JoinColumn(name = "guide_id")
    private User guide;

    // The Vehicle assigned to this trip (Can be null initially)
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Feedback feedback;
}
