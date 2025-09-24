package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByBill_Booking_User_Id(String userId, Pageable pageable);
}
