package com.example.OpenSky.repositories;

import com.example.OpenSky.entities.HotelRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, String> {
    Page<HotelRoom> findByHotel_Id(String hotelId, Pageable pageable);
    Page<HotelRoom> findByRoomNameContainingIgnoreCaseAndHotel_Id(String roomName, String hotelId,Pageable pageable);
}

