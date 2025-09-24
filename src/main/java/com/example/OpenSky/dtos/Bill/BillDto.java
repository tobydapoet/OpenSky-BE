package com.example.OpenSky.dtos.Bill;

import com.example.OpenSky.dtos.User.UserSummaryDto;
import com.example.OpenSky.entities.Bill;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.BillStatus;
import com.example.OpenSky.enums.TableTypeBill;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BillDto {
    private String id;
    private Double deposit;
    private Double refundPrice;
    private Double totalPrice;
    private BillStatus status;
    private LocalDateTime createdAt;

    public static BillDto fromEntity(Bill bill) {
        return  new BillDto(
                bill.getId(),
                bill.getDeposit(),
                bill.getRefundPrice(),
                bill.getTotalPrice(),
                bill.getStatus(),
                bill.getCreatedAt()
        );
    }
}
