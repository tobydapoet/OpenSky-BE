package com.example.OpenSky.dtos.BillDetail;

import com.example.OpenSky.dtos.Bill.BillDto;
import com.example.OpenSky.entities.Bill;
import com.example.OpenSky.entities.BillDetail;
import com.example.OpenSky.enums.TableTypeBill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BillDetailDto {
    private String id;

    private BillDto bill;

    private Integer quantity;

    private Integer unitPrice;

    private Integer totalPrice;

    private String notes;

    private LocalDateTime createdAt;

    private Object target;

    public static BillDetailDto fromEntity(BillDetail billDetail) {
        return new BillDetailDto(
                billDetail.getId(),
                BillDto.fromEntity(billDetail.getBill()),
                billDetail.getQuantity(),
                billDetail.getUnitPrice(),
                billDetail.getTotalPrice(),
                billDetail.getNotes(),
                billDetail.getCreatedAt(),
                null
        );
    }
}
