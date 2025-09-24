package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Refund.RefundDto;
import com.example.OpenSky.entities.Refund;
import com.example.OpenSky.requests.Refund.RefundRequest;
import com.example.OpenSky.services.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refunds")
public class RefundController {
       @Autowired
       private RefundService refundService;

       @Operation(summary = "Lấy tất cả refund",
               security = { @SecurityRequirement(name = "bearerAuth")}
       )
       @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
       @GetMapping
       public Page<RefundDto> getRefunds(
               @RequestParam(defaultValue = "0") int page,
               @RequestParam(defaultValue = "10") int size
       ){
           Page<Refund> refunds = refundService.findAll(page,size);
           return refunds.map(RefundDto::fromEntity);
       }

       @GetMapping("/bill/{id}")
       @Operation(summary = "Lấy theo billId",
               security = { @SecurityRequirement(name = "bearerAuth")}
       )
       public RefundDto  findByBillId(@PathVariable String billId) {
           Refund refund = refundService.findByBillId(billId);
           return RefundDto.fromEntity(refund);
       }

        @Operation(summary = "Xem yêu cầu hoàn tiền theo billId",
                security = { @SecurityRequirement(name = "bearerAuth")}
        )
       @GetMapping("/{id}")
       public RefundDto findById(@PathVariable String id) {
           Refund refund = refundService.findByBillId(id);
           return RefundDto.fromEntity(refund);
       }

        @Operation(summary = "Tạo yêu cầu hoàn tiền",
                security = { @SecurityRequirement(name = "bearerAuth")}
        )
        @PreAuthorize("hasRole('CUSTOMER')")
           @PostMapping
           public RefundDto create(@RequestBody RefundRequest req) {
               Refund refund = refundService.create(req);
               return RefundDto.fromEntity(refund);
           }
}
