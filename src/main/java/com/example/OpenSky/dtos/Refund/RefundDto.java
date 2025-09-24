package com.example.OpenSky.dtos.Refund;

import com.example.OpenSky.dtos.Bill.BillDto;
import com.example.OpenSky.entities.Refund;
import com.example.OpenSky.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RefundDto {
    private String id;
    private String description;
    private LocalDateTime createdAt;
    private BillDto billDto;

    public static RefundDto fromEntity(Refund refund) {
        return new RefundDto(
                refund.getDescription(),
                refund.getId(),
                refund.getCreatedAt(),
                BillDto.fromEntity(refund.getBill())
        );
    }
}
