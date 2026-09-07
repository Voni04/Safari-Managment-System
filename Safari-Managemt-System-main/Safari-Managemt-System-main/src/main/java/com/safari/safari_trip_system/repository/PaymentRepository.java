package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
