package com.example.OpenSky.services;

import com.example.OpenSky.dtos.BillDetail.BillDetailDto;
import com.example.OpenSky.dtos.Booking.BookingDto;
import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.dtos.HotelRoom.HotelRoomDto;
import com.example.OpenSky.dtos.Schedule.ScheduleDto;
import com.example.OpenSky.dtos.Tour.TourDto;
import com.example.OpenSky.entities.*;
import com.example.OpenSky.enums.HotelRoomStatus;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import com.example.OpenSky.repositories.BillDetailRepository;
import com.example.OpenSky.requests.BillDetail.BillDetailCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HotelRoomService hotelRoomService;

    public BillDetail findById(String id) {
        return  billDetailRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy item này!")
        );
    }

    public BillDetailDto getBillDetailDto(BillDetail billDetail) {
            BillDetailDto billDetailDto =  BillDetailDto.fromEntity(billDetail);

            Object target = null;
            if (TableTypeBill.HotelRoom.equals(billDetail.getItemType())) {
                HotelRoom hotelRoom = hotelRoomService.findById(billDetail.getItemId());
                target = hotelRoom != null ? HotelRoomDto.fromEntity(hotelRoom,null) : null;
            } else if (TableTypeBill.Schedule.equals(billDetail.getItemType())) {
                Schedule schedule = scheduleService.findById(billDetail.getItemId());
                target = schedule != null ? ScheduleDto.fromEntity(schedule) : null;
            }
            billDetailDto.setTarget(target);
            return billDetailDto;
    }

    public List<BillDetail> findByBillId(String billId) {
        return billDetailRepository.findByBill_Id(billId);
    }

    public BillDetail create(BillDetailCreateRequest req) {
        BillDetail billDetail = new BillDetail();
        billDetail.setItemType(req.getItemType());
        billDetail.setQuantity(req.getQuantity());
        billDetail.setNotes(req.getNotes());
        int unitPrice = 0;
        if(req.getItemType() == TableTypeBill.HotelRoom) {
            HotelRoom hotelRoom = hotelRoomService.findById(req.getItemId());
            if(hotelRoom.getStatus() != HotelRoomStatus.Active) {
                throw new RuntimeException("Phòng này đang không hoạt động!");
            }
            else {
                hotelRoomService.updateStatus(req.getItemId(), HotelRoomStatus.Active);
            }
            billDetail.setItemId(hotelRoom.getId());
            unitPrice = hotelRoom.getPrice();
            billDetail.setUnitPrice(unitPrice);
            billDetail.setTotalPrice(unitPrice * req.getQuantity());
        }
        if(req.getItemType() == TableTypeBill.Schedule) {
            Schedule schedule = scheduleService.findById(req.getItemId());
            scheduleService.updatePeople(req.getItemId(),req.getQuantity());
            billDetail.setItemId(schedule.getId());
            unitPrice = schedule.getTour().getPrice();
            billDetail.setUnitPrice(unitPrice);
            billDetail.setTotalPrice(unitPrice * req.getQuantity());
        }
        return billDetailRepository.save(billDetail);
    }

    public void delete(String id) {
        BillDetail billDetail = findById(id);
        billDetailRepository.delete(billDetail);
    }
}
