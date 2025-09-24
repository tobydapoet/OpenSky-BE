package com.example.OpenSky.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "TourItinerary")
public class TourItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    private Integer dayNumber;

    private String location;

    private String description;

    @OneToMany(mappedBy = "tourItinerary")
    private List<ScheduleItinerary> scheduleItineraries;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
