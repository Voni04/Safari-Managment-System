package com.safari.safari_trip_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saved_cards")
public class SavedCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardHolderName;

    // NOTE: In a real production app, never store plain card numbers!
    // We do this here ONLY for the university demo simulation.
    private String cardNumber;

    private String expiryDate;
    private String cvv;

    // Link to User (One User has One Saved Card for simplicity)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}