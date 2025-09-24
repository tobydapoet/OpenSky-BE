package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Bill.BillDto;
import com.example.OpenSky.entities.Bill;
import com.example.OpenSky.enums.BillStatus;
import com.example.OpenSky.services.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/bills")
@RestController
public class BillController {
    @Autowired
    private BillService billService;

    @Operation(summary = "Lấy ra thông tin người dùng theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/user/")
    public Page<BillDto> findByUserId(
            @RequestParam String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Bill> bills = billService.findByUserId(id,page,size);
         return bills.map(BillDto::fromEntity);
    }

    @Operation(summary = "Lấy ra thông tin người dùng theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping
    public BillDto findById(@RequestParam String id) {
        Bill bill = billService.findById(id);
        return BillDto.fromEntity(bill);
    }


    @Operation(summary = "Lấy ra thông tin người dùng theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PutMapping("/status/{id}")
    public BillDto changeStatus(@PathVariable String id, @RequestBody BillStatus status) {
        Bill bill = billService.changeStatus(id,status);
        return BillDto.fromEntity(bill);
    }

}
