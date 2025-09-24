package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    Page<Schedule> findByUser_Id(String userId, Pageable pageable);
    Page<Schedule> findByTour_Id(String tourId, Pageable pageable);
}
