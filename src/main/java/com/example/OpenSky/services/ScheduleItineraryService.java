package com.example.OpenSky.services;

import com.example.OpenSky.entities.Schedule;
import com.example.OpenSky.entities.ScheduleItinerary;
import com.example.OpenSky.entities.TourItinerary;
import com.example.OpenSky.repositories.ScheduleItineraryRepository;
import com.example.OpenSky.requests.ScheduleItinerary.ScheduleItineraryCreateRequest;
import com.example.OpenSky.requests.ScheduleItinerary.ScheduleItineraryUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleItineraryService {
    @Autowired
    private ScheduleItineraryRepository scheduleItineraryRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TourItineraryService tourItineraryService;

    public List<ScheduleItinerary> findByScheduleId(String tourId) {
        return scheduleItineraryRepository.findBySchedule_Id(tourId);
    }

    public ScheduleItinerary findById(String id) {
        return scheduleItineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this itinerary!"));
    }

    public List<ScheduleItinerary> findByTourItineraryId(String tourItineraryId) {
        return scheduleItineraryRepository.findByTourItinerary_Id(tourItineraryId);
    }

    public ScheduleItinerary create(ScheduleItineraryCreateRequest req) {
        ScheduleItinerary scheduleItinerary = new ScheduleItinerary();
        TourItinerary tourItinerary = tourItineraryService.findById(req.getTourItineraryId());
        Schedule schedule = scheduleService.findById(req.getScheduleId());
        scheduleItinerary.setSchedule(schedule);
        scheduleItinerary.setTourItinerary(tourItinerary);
        scheduleItinerary.setEndTime(req.getEndTime());
        return scheduleItineraryRepository.save(scheduleItinerary);
    }

    public ScheduleItinerary update(String id, ScheduleItineraryUpdateRequest req) {
        ScheduleItinerary scheduleItinerary = findById(id);
        scheduleItinerary.setEndTime(req.getEndTime());
        return scheduleItineraryRepository.save(scheduleItinerary);
    }

    public void deleteById(String id) {
        ScheduleItinerary scheduleItinerary = findById(id);
        scheduleItineraryRepository.delete(scheduleItinerary);
    }
}
