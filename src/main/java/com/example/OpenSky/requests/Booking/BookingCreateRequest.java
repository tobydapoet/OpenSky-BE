package com.example.OpenSky.requests.Booking;

import com.example.OpenSky.entities.BillDetail;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import com.example.OpenSky.requests.BillDetail.BillDetailCreateRequest;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class BookingCreateRequest {
    @NotBlank(message = "type table không được bỏ trống")
    private TableType tableType;

    @NotBlank(message = "type id không được bỏ trống")
    private String typeId;

    @NotNull(message = "Checkin không được bỏ trống")
    private LocalDateTime checkInDate;

    @NotNull(message = "Checkout không được bỏ trống")
    private LocalDateTime checkOutDate;

    private String notes;

    @NotNull(message = "Tiền thanh toán không được bỏ trống")
    private Double deposit;

    private Double refundPrice;

    private String userVoucherId;

    private List<BillDetailCreateRequest> bookingDetails;
}
