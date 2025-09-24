package com.example.OpenSky.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "Tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Min(0)
    @Max(5)
    private Integer star = 0;

    @Column(nullable = false)
    private Integer price = 0;

    private Integer numberOfDays;

    private Integer maxPeople;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
    private List<Schedule> schedules;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
    private List<TourItinerary> tourItineraries;
}

