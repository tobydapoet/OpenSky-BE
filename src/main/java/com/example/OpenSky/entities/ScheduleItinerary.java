package com.example.OpenSky.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "ScheduleItinerary")
public class ScheduleItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private TourItinerary tourItinerary;

    @CreationTimestamp
    private LocalDateTime startTime;

    @JoinColumn(nullable = false)
    private LocalDateTime endTime;

}
