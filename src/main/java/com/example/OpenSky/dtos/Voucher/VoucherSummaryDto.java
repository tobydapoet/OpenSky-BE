package com.example.OpenSky.dtos.Voucher;

import com.example.OpenSky.entities.Voucher;
import com.example.OpenSky.enums.TableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSummaryDto {
    private String id;

    private TableType tableType;

    private String description;

    private Integer percent;


    public static VoucherSummaryDto fromEntity(Voucher voucher) {
        return new VoucherSummaryDto(
                voucher.getId(),
                voucher.getTableType(),
                voucher.getDescription(),
                voucher.getPercent()
        );
    }
}
