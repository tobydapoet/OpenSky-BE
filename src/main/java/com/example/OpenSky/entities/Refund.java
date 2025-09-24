package com.example.OpenSky.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "bill_id",nullable =   false)
    private Bill bill;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
