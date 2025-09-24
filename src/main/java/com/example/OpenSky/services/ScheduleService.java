package com.example.OpenSky.services;

import com.example.OpenSky.entities.Schedule;
import com.example.OpenSky.entities.Tour;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.repositories.ScheduleRepository;
import com.example.OpenSky.requests.Schedule.ScheduleCreateRequest;
import com.example.OpenSky.requests.Schedule.ScheduleUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TourService tourService;

    public Page<Schedule> findByUser_Id(String userId, int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findByUser_Id(userId, pageable);
    }

    public Page<Schedule> findByTour_Id(String tourId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findByTour_Id(tourId, pageable);
    }

    public Schedule findById(String id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch trình"));
    }

    public void updatePeople(String id,int people) {
        Schedule schedule = findById(id);
        schedule.setNumberPeople(schedule.getNumberPeople() + people);
        int count = schedule.getNumberPeople();
        if(count > schedule.getTour().getMaxPeople()) {
            throw new RuntimeException("Đã đạt quá giới hạn người cho phép!");
        }
        scheduleRepository.save(schedule);
    }

    public void updateMinorPeople(String id, int people) {
        Schedule schedule = findById(id);
        int current = schedule.getNumberPeople();
        if (people > current) {
            throw new RuntimeException("Số người cần trừ vượt quá số người hiện có trong lịch trình!");
        }
        schedule.setNumberPeople(current - people);
        scheduleRepository.save(schedule);
    }

    public Schedule create(ScheduleCreateRequest req) {
        Schedule schedule = new Schedule();
        User user = userService.findById(req.getUserId());
        schedule.setUser(user);
        Tour tour = tourService.findById(req.getTourId());
        schedule.setTour(tour);
        schedule.setStartTime(req.getStartTime());
        schedule.setEndTime(req.getStartTime().plusDays(tour.getNumberOfDays()));
        return scheduleRepository.save(schedule);
    }

    public Schedule update(String id,ScheduleUpdateRequest req) {
        Schedule schedule = findById(id);
        int tourDaysNumber = schedule.getTour().getNumberOfDays();
        schedule.setStartTime(req.getStartTime());
        schedule.setEndTime(req.getStartTime().plusDays(tourDaysNumber));
        return scheduleRepository.save(schedule);
    }
}
