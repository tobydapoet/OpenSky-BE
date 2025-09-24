package com.example.OpenSky.requests.Voucher;

import com.example.OpenSky.enums.TableType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherCreateRequest {

    @NotNull
    private TableType tableType;

    @NotNull
    private Integer percent;

    @Future(message = "StartDate should greater than today!")
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private String description;

    @AssertTrue(message = "StartDate should be smaller than EndDate!")
    @JsonIgnore
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return startDate.isBefore(endDate);
    }
}
