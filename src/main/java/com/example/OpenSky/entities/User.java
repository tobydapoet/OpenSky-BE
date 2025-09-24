package com.example.OpenSky.entities;

import com.example.OpenSky.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "User")
@JsonIgnoreProperties({
        "sessions",
        "hotel",
        "messageSender",
        "messageReceiver",
        "tours",
        "hibernateLazyInitializer",
        "handler",
        "userVouchers",
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    private String fullName;

    @Column(unique = true)
    private String providerId;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.Customer ;

    @Column( unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String citizenId;

    private LocalDate dob;

    private String avatarURL;

    @CreationTimestamp
    private
    LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "senderUser")
    private List<Message> messageSender = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiverUser")
    private List<Message> messageReceiver = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Hotel hotel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Tour> tours;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserVoucher> userVouchers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private  List<Session> sessions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();
}
