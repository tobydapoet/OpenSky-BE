package com.example.OpenSky.services;

import com.example.OpenSky.dtos.Feedback.FeedbackDto;
import com.example.OpenSky.dtos.Feedback.FeedbackSummaryDto;
import com.example.OpenSky.entities.Bill;
import com.example.OpenSky.entities.Feedback;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.repositories.FeedBackRepository;
import com.example.OpenSky.requests.FeedBack.FeedBackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TourService tourService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillService billService;

    public Feedback findById(String id) {
        return feedBackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this feedback"));
    }

    public FeedbackDto findByIdWithTable(String id)
    {
        Feedback feedback = this.findById(id);
        return FeedbackDto.fromEntity(feedback,hotelService,tourService);
    }

    public List<FeedbackSummaryDto> findByTableIdAndTableType(String typeId, TableType tableType) {
        tourService.findById(typeId);
        List<Feedback> feedbacks = feedBackRepository.findByTableIdAndTableType(typeId, tableType);
        return feedbacks.stream()
                .map(FeedbackSummaryDto::fromEntity)
                .toList();
    }

    public List<FeedbackSummaryDto> findByTour(String tourId) {
        hotelService.findById(tourId);
        List<Feedback> feedbacks = feedBackRepository.findByTableIdAndTableType(tourId,TableType.Tour);
        return feedbacks.stream()
                .map(FeedbackSummaryDto::fromEntity)
                .toList();
    }

    public List<FeedbackSummaryDto> findByHotel(String hotelId) {
        List<Feedback> feedbacks = feedBackRepository.findByTableIdAndTableType(hotelId,TableType.Hotel);
        return feedbacks.stream()
                .map(FeedbackSummaryDto::fromEntity)
                .toList();
    }

    public FeedbackDto create(FeedBackRequest req, String token) {
        Feedback feedback = new Feedback();
        feedback.setDescription(req.getDescription());
        feedback.setRate(req.getRate());
        feedback.setTableType(req.getTableType());
        feedback.setTableId(req.getTableId());
        Bill bill = billService.findById(req.getBillId());
        User user = userService.findCurrentUser(token);
        feedback.setBill(bill);
        feedback.setUser(user);
        return FeedbackDto.fromEntity(feedback,hotelService,tourService);
    }

    public void deleteById(String id) {
        Feedback feedback = this.findById(id);
        feedBackRepository.delete(feedback);
    }
}
