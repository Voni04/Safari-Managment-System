package com.safari.safari_trip_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating; // 1 to 5 stars

    @Column(nullable = false, length = 1000)
    private String comments;

    @Column(length = 1000)
    private String adminReply; // Admin writes this later

    private LocalDateTime submitDate;

    // Link feedback to a specific Booking (One-to-One: 1 Booking = 1 Feedback)
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;
}