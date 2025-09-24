package com.example.OpenSky.entities;

import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private User user;

    @Column(nullable = false)
    private TableType tableType;

    @Column(nullable = false)
    private String typeId;

    @Column(nullable=false)
    private LocalDateTime checkInDate;

    @Column(nullable=false)
    private LocalDateTime checkOutDate;

    private String notes;

    @OneToOne(mappedBy = "booking")
    private Bill bill;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
