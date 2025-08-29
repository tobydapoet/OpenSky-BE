package com.example.OpenSky.entities;

import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeImage;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "VoucherId")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableType tableType;

    @Column(name = "table_id", nullable = false)
    private String tableId;

    @Column( nullable = false)
    private Date startDate;

    @Column( nullable = false)
    private Date endDate;

    @Column( nullable = false)
    private String description;

    @Column( nullable = false)
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
