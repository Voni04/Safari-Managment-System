package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.entity.SavedCard;
import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.repository.SavedCardRepository;
import com.safari.safari_trip_system.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SavedCardService {

    private final SavedCardRepository savedCardRepository;
    private final UserRepository userRepository;

    public SavedCardService(SavedCardRepository savedCardRepository, UserRepository userRepository) {
        this.savedCardRepository = savedCardRepository;
        this.userRepository = userRepository;
    }

    public void saveCard(String username, String holder, String number, String expiry, String cvv) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user already has a saved card. If so, update it.
        SavedCard card = savedCardRepository.findByUser(user).orElse(new SavedCard());

        card.setUser(user);
        card.setCardHolderName(holder);
        card.setCardNumber(number);
        card.setExpiryDate(expiry);
        card.setCvv(cvv);

        savedCardRepository.save(card);
    }

    public SavedCard getCard(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return savedCardRepository.findByUser(user).orElse(null);
    }
}