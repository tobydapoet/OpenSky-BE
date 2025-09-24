package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.entities.Hotel;
import com.example.OpenSky.enums.HotelStatus;
import com.example.OpenSky.requests.Hotel.HotelRequest;
import com.example.OpenSky.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Lấy ra thông tin khách sạn",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping()
    public Page<HotelDto> getHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        return hotelService.getHotels(page, size);
    }

    @Operation(summary = "Lấy ra thông tin khách sạn dựa trên id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/{id}")
    public HotelDto findById(@PathVariable String id) {
        return hotelService.findByIdWithImage(id);
    }

    @GetMapping("/search")
    public Page<HotelDto> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return hotelService.searchHotel(keyword, page, size);
    }

    @Operation(summary = "Tạo khách sạn",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> createHotel(@ModelAttribute HotelRequest req) {
        HotelDto hotel =  hotelService.create(req);
        Map<String , String> map = new HashMap<>();
        map.put("message","Tạo khách sạn thành công!");
        map.put("id", hotel.getId());
        return map;
    }

    @Operation(summary = "Cập nhật khách sạn theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR','HOTEL')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,String> updateHotel(@PathVariable String id,@ModelAttribute HotelRequest req) {
        Hotel hotel = hotelService.update(id,req);
        Map<String , String> map = new HashMap<>();
        map.put("message","Cập nhật khách sạn thành công!");
        map.put("id", hotel.getId());
        return map;
    }

    @Operation(summary = "Cập nhật trạng thái khách sạn",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasRole('CUSTOMER','ADMIN','SUPERVISOR')")
    @PutMapping("status/{id}")
    public Map<String,String> updateStatus(@PathVariable String id,@ModelAttribute HotelStatus status) {
        Hotel hotel = hotelService.updateRole(id, status);
        Map<String,String> map = new HashMap<>();
        map.put("message","Cập nhật trạng thái khách sạn thành công!");
        map.put("hotelId", hotel.getId());
        return map;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping(value = "profile/{id}", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,Object> updateProfile(HttpServletRequest httpReq, @ModelAttribute HotelRequest req) {
        String authToken = httpReq.getHeader("Authorization");
        String token = authToken.replace("Bearer ", "");
        Hotel hotel = hotelService.updateCurrentHotel(token, req);
        Map<String,Object> map = new HashMap<>();
        map.put("message","Cập nhật thành công!");
        map.put("id", hotel.getId());
        return map;
    }

}
