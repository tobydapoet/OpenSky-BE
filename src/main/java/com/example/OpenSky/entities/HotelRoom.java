package com.example.OpenSky.entities;

import com.example.OpenSky.enums.HotelRoomStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "HotelRoom")
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch =  FetchType.EAGER)
    private Hotel hotel;

    private String roomName;

    private String roomType;

    private String address;

    private Integer price;

    private HotelRoomStatus status = HotelRoomStatus.Active;

    private Integer maxPeople;
}
