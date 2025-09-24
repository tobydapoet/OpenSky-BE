package com.example.OpenSky.services;

import com.example.OpenSky.entities.Tour;
import com.example.OpenSky.entities.TourItinerary;
import com.example.OpenSky.repositories.TourItineraryRepository;
import com.example.OpenSky.requests.TourItinerary.TourItineraryCreateRequest;
import com.example.OpenSky.requests.TourItinerary.TourItineraryUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourItineraryService {
    @Autowired
    private TourItineraryRepository tourItineraryRepository;

    @Autowired
    private TourService tourService;

    public List<TourItinerary> findByTourId(String tourId) {
        return tourItineraryRepository.findByTour_IdOrderByCreatedAtDesc(tourId);
    }

    public TourItinerary findById(String id) {
        return tourItineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TourItinerary not found!"));
    }

    public TourItinerary create(TourItineraryCreateRequest req) {
        TourItinerary tourItinerary = new TourItinerary();
        Tour tour = tourService.findById(req.getTourId());
        tourItinerary.setTour(tour);
        tourItinerary.setDescription(req.getDescription());
        tourItinerary.setDayNumber(req.getDayNumber());
        tourItinerary.setLocation(req.getLocation());
        return tourItineraryRepository.save(tourItinerary);
    }

    public TourItinerary update(String id, TourItineraryUpdateRequest req) {
        TourItinerary tourItinerary = findById(id);
        tourItinerary.setDescription(req.getDescription());
        tourItinerary.setDayNumber(req.getDayNumber());
        tourItinerary.setLocation(req.getLocation());
        return tourItineraryRepository.save(tourItinerary);
    }

    public void delete(String id) {
        TourItinerary tourItinerary = findById(id);
        tourItineraryRepository.delete(tourItinerary);
    }
}
