package com.example.OpenSky.entities;

import com.example.OpenSky.enums.BillStatus;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import com.example.OpenSky.enums.TableTypeImage;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserVoucher userVoucher;

    @Column(nullable = false)
    private TableTypeBill tableType;

    @Column(nullable = false)
    private String typeId;

    @Column(nullable = false)
    private Double deposit;

    @Column(nullable = false)
    private Double refundPrice;

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private BillStatus status =BillStatus.Pending;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "bill")
    private Refund refund;

    @OneToMany(mappedBy = "bill")
    private List<Notification> notifications;

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserVoucher getUserVoucher() {
        return userVoucher;
    }

    public void setUserVoucher(UserVoucher userVoucher) {
        this.userVoucher = userVoucher;
    }

    public TableTypeBill getTableType() {
        return tableType;
    }

    public void setTableType(TableTypeBill tableType) {
        this.tableType = tableType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
