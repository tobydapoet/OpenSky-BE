package com.example.OpenSky.dtos.Voucher;

import com.example.OpenSky.entities.Voucher;
import com.example.OpenSky.enums.TableType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class VoucherDto extends VoucherSummaryDto{
    private LocalDate startDate;

    private LocalDate endDate;

    public static VoucherDto fromEntity(Voucher voucher) {
        return new VoucherDto(
                voucher.getId(),
                voucher.getTableType(),
                voucher.getDescription(),
                voucher.getPercent(),
                voucher.getStartDate(),
                voucher.getEndDate()
        );
    }

    public VoucherDto(String id, TableType tableType, String description, Integer percent, LocalDate startDate, LocalDate endDate) {
        super(id, tableType, description, percent);
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
