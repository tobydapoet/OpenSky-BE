package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,String> {
     Page<Booking> findByUser_Id(String userId, Pageable pageable);
     Page<Booking> findByUser_FullNameContainingIgnoreCase(String fullName, Pageable pageable);
}
