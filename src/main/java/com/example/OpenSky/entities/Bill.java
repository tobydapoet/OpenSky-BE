package com.example.OpenSky.entities;

import com.example.OpenSky.enums.BillStatus;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import com.example.OpenSky.enums.TableTypeImage;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Bill")
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_voucher_id")
    private UserVoucher userVoucher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status = BillStatus.Pending;

    @Column(nullable = false)
    private Double deposit;

    @Column(nullable = false)
    private Double refundPrice;

    @Column(nullable = false)
    private Double totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL)
    private Refund refund;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillDetail> billDetails;

    @OneToOne(mappedBy = "bill")
    private Feedback feedback;
}
