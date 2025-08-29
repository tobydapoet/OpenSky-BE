package com.example.OpenSky.entities;

import jakarta.persistence.*;

import java.security.PrivateKey;
import java.util.List;
import java.util.UUID;

@Entity
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ScheduleItinerary> getScheduleItineraries() {
        return scheduleItineraries;
    }

    public void setScheduleItineraries(List<ScheduleItinerary> scheduleItineraries) {
        this.scheduleItineraries = scheduleItineraries;
    }
}
