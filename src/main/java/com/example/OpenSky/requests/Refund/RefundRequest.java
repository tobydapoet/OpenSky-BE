package com.example.OpenSky.requests.Refund;

import com.example.OpenSky.entities.Bill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundRequest {
    @NotBlank(message = "id hóa đơn không được bỏ trống")
    private String billId;

    @NotBlank(message = "lý do không được bỏ trống")
    private String description;
}
