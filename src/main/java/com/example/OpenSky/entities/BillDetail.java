package com.example.OpenSky.entities;

import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "BillDetail")
@Data
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private TableTypeBill itemType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId")
    private Bill bill;

    @Column(nullable = false)
    private String itemId;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer totalPrice;

    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
