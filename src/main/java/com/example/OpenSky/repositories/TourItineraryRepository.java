package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.TourItinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourItineraryRepository extends JpaRepository<TourItinerary, String> {
    List<TourItinerary> findByTour_IdOrderByCreatedAtDesc(String tourId);
}
