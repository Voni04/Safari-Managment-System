package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}