package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TourRepository extends JpaRepository<Tour, String> {
    Page<Tour> findByNameContainingIgnoreCase(String district, Pageable pageable);
}
