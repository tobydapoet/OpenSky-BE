package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.ScheduleItinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleItineraryRepository extends JpaRepository<ScheduleItinerary, String> {
    List<ScheduleItinerary> findBySchedule_Id(String scheduleId);
    List<ScheduleItinerary> findByTourItinerary_Id(String TourItineraryId);

}
