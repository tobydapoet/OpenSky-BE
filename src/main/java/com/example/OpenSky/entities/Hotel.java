package com.example.OpenSky.entities;

import com.example.OpenSky.enums.HotelStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "Hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column( nullable = false)
    private String address;

    private String province;

    @Column(precision = 18, scale = 15)
    private BigDecimal latitude;

    @Column(precision = 18, scale = 15)
    private BigDecimal longitude;

    @Column( nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private HotelStatus status = HotelStatus.Inactive;

    @Min(0)
    @Max(5)
    @Column(name = "star")
    private Integer star = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "hotel")
    private List<HotelRoom> hotelRooms;
}
