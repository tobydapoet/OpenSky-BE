package com.example.OpenSky.entities;

import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableType tableType;

    @Column(nullable = false)
    private Integer percent;

    @FutureOrPresent(message = "StartDate should be today or greater!")
    @Column( nullable = false)
    private LocalDate startDate;

    @Column( nullable = false)
    private LocalDate endDate;

    @Column( nullable = false)
    private String description;

    @AssertTrue(message = "StartDate should be smaller than EndDate!")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return startDate.isBefore(endDate);
    }
}
