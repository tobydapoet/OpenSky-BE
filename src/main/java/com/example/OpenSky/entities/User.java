package com.example.OpenSky.entities;

import com.example.OpenSky.enums.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "User")
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
    private String phone;

    @Column(unique = true)
    private String citizenId;

    private Date dob;

    private String avatarUrl;

    @CreationTimestamp
    private Date createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "senderUser") // mappedBy trỏ đến field bên Message
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(List<Message> messageSender) {
        this.messageSender = messageSender;
    }

    public List<Message> getMessageReceiver() {
        return messageReceiver;
    }

    public void setMessageReceiver(List<Message> messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    public List<UserVoucher> getUserVouchers() {
        return userVouchers;
    }

    public void setUserVouchers(List<UserVoucher> userVouchers) {
        this.userVouchers = userVouchers;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
