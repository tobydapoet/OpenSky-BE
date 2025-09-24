package com.example.OpenSky.services;

import com.example.OpenSky.entities.*;
import com.example.OpenSky.enums.BillStatus;
import com.example.OpenSky.enums.HotelRoomStatus;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.enums.TableTypeBill;
import com.example.OpenSky.repositories.BillRepository;
import com.example.OpenSky.requests.Bill.BillCreateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserVoucherService userVoucherService;

    @Autowired
    @Lazy
    private BookingService bookingService;

    @Autowired
    private HotelRoomService hotelRoomService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RefundService refundService;

    @Autowired
    private BillDetailService billDetailService;

    public Bill findById(String id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn này không tồn tại"));
    }

    public Page<Bill> findByUserId(String userId,int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        return  billRepository.findByBooking_User_Id(userId,pageable);
    }

    public Bill create(BillCreateRequest req) {
        Bill bill = new Bill();
        Booking booking = bookingService.findById(req.getBookingId());
        bill.setBooking(booking);
        bill.setDeposit(req.getDeposit());
        if(req.getUserVoucherId()!=null){
            UserVoucher userVoucher = userVoucherService.findById(req.getUserVoucherId());
            if(userVoucher.getVoucher().getEndDate().isAfter(LocalDate.now())){
                throw new RuntimeException("Voucher này đã hết hạn!");
            }
            userVoucherService.setUsedVoucher(req.getUserVoucherId());
            bill.setUserVoucher(userVoucher);
        }
        return billRepository.save(bill);
    }

    public void updateTotalPrice(String id) {
        List<BillDetail> billDetails = billDetailService.findByBillId(id);
       double total = 0.0;
        for(BillDetail billDetail : billDetails){
            total += billDetail.getTotalPrice();
        }
        Bill bill = findById(id);
        if(bill.getUserVoucher()!=null){
            total = total * (100 - bill.getUserVoucher().getVoucher().getPercent()) / 100;
        }
        bill.setTotalPrice(total);
        billRepository.save(bill);
    }

    public Bill changeStatus(String id, BillStatus status) {
        Bill bill = findById(id);
        bill.setStatus(status);
        return billRepository.save(bill);
    }

    @Transactional
    public Bill update(String id) {
        Bill bill = findById(id);
        Refund refund = refundService.findByBillId(id);
        bill.setRefund(refund);

        LocalDate refundDate = refund.getCreatedAt().toLocalDate();
        LocalDate checkInDate = bill.getBooking().getCheckInDate().toLocalDate();
        LocalDate checkOutDate = bill.getBooking().getCheckOutDate().toLocalDate();

        if (refundDate.isAfter(checkInDate)) {
            throw new RuntimeException("Lịch trình đã diễn ra, không thể hoàn tiền!");
        }

        long daysBetween = ChronoUnit.DAYS.between(refundDate, checkOutDate);

        double refundPercent;
        if (daysBetween <= 3) {
            refundPercent = 0.1;
            bill.setStatus(BillStatus.Refunded);
        }
        else if (daysBetween < 7) {
            refundPercent = 0.5;
            bill.setStatus(BillStatus.Refunded);
        }
        else {
            refundPercent = 1.0;
            bill.setStatus(BillStatus.Cancelled);
        }
        List<BillDetail> billDetails = billDetailService.findByBillId(id);
        for (BillDetail detail : billDetails) {
            if (TableTypeBill.HotelRoom.equals(detail.getItemType())) {
                HotelRoom room = hotelRoomService.findById(detail.getItemId());
               hotelRoomService.updateStatus(room.getId(), HotelRoomStatus.Active);
            } else if (TableTypeBill.Schedule.equals(detail.getItemType())) {
                Schedule schedule = scheduleService.findById(detail.getItemId());
                scheduleService.updateMinorPeople(schedule.getId(), detail.getQuantity());
            }
        }
        bill.setRefundPrice(bill.getTotalPrice() * refundPercent);
        return billRepository.save(bill);
    }
}
