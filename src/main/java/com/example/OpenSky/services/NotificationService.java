package com.example.OpenSky.services;

import com.example.OpenSky.entities.*;
import com.example.OpenSky.enums.BillStatus;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import com.example.OpenSky.repositories.NotificationRepository;
import com.example.OpenSky.requests.Notification.NotificationRequest;
import jakarta.annotation.Resource;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TourService tourService;

    @Autowired
    private UserService userService;


    public Notification create(NotificationRequest req) {
        Notification notification = new Notification();
        Bill bill = billService.findById(req.getBillId());
        notification.setBill(bill);

        String linkedName = getLinkedName(bill);
        notification.setDescription(buildDescription(bill.getStatus(), linkedName));

        Notification savedNotification = notificationRepository.save(notification);

        simpMessagingTemplate.convertAndSendToUser(
                bill.getBooking().getUser().getId(),
                "/topic/notifications",
                savedNotification
        );

        return savedNotification;
    }

    private String getLinkedName(Bill bill) {
        if (bill.getBooking().getTableType() == null || bill.getBooking().getTypeId() == null) return "";

        return switch (bill.getBooking().getTableType()) {
            case Hotel -> hotelService.findById(bill.getBooking().getId()).getName();
            case Tour -> tourService.findById(bill.getBooking().getId()).getName();
        };
    }

    private String buildDescription(BillStatus status, String linkedName) {
        return switch (status) {
            case Pending -> "Vui lòng thanh toán bill đi " + linkedName;
            case Paid -> "Bạn đã thanh toán thành công bill đi " + linkedName;
            case Cancelled -> "Bill đi " + linkedName + " đã bị hủy.";
            case Refunded -> "Bill đi " + linkedName + " đã được hoàn tiền.";
        };
    }

    public List<Notification> findByUserId(String token) {
        User user = userService.findCurrentUser(token);
        return notificationRepository.findByBill_Booking_User_Id(
                user.getId(),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

    }
}
