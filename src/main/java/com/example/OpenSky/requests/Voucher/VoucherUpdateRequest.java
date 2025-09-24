package com.example.OpenSky.requests.Voucher;

import com.example.OpenSky.enums.TableType;
import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherUpdateRequest {

    private TableType tableType;

    @Future(message = "StartDate should greater than today!")
    private LocalDate startDate;

    private LocalDate endDate;

    private String description;
}
