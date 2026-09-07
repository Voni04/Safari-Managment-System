package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.SavedCard;
import com.safari.safari_trip_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SavedCardRepository extends JpaRepository<SavedCard, Long> {
    Optional<SavedCard> findByUser(User user);
}
