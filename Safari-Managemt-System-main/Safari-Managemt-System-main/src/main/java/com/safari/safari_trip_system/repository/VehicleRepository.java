package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
