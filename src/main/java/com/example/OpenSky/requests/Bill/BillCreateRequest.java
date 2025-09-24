package com.example.OpenSky.requests.Bill;
import com.example.OpenSky.entities.Booking;
import com.example.OpenSky.enums.TableTypeBill;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BillCreateRequest {
    private String userVoucherId;

    @NotBlank(message = "BookingId không được bỏ trống")
    private String bookingId;

    @NotBlank(message = "Tiền thanh toán không được bỏ trống")
    private Double deposit;

}
