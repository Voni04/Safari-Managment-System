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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    private LocalDateTime timestamp;

    // New Field: Has the receiver seen this?
    // Default is false
    private boolean isRead = false;

    // Who sent it?
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Who is it for?
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
}