package com.example.OpenSky.requests.BillDetail;

import com.example.OpenSky.entities.Bill;
import com.example.OpenSky.enums.TableTypeBill;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillDetailCreateRequest {
    @NotBlank(message = "type item không được bỏ trống")
    private TableTypeBill itemType;

    @NotBlank(message = "id hóa đơn không được bỏ trống")
    private String billId;

    @NotBlank(message = "item id hóa đơn không được để trống")
    private String itemId;

    private Integer quantity ;

    private String notes;
}
