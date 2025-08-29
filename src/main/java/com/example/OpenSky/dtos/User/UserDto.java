package com.example.OpenSky.dtos.User;

import jakarta.persistence.Column;

import java.util.Date;
import java.util.UUID;

public class UserDto extends RegisterDto{
    private String phone;

    private String citizenId;

    private Date dob;

    private String avatarUrl;

    public UserDto(String id, String email, String fullName, Date createdAt, String phone, String citizenId, Date dob, String avatarUrl) {
        super(id, email, null, fullName, createdAt);
        this.phone = phone;
        this.citizenId = citizenId;
        this.dob = dob;
        this.avatarUrl = avatarUrl;
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
}
