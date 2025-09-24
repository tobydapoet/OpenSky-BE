package com.example.OpenSky.services;
import com.example.OpenSky.dtos.Booking.BookingDto;
import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.dtos.Tour.TourDto;
import com.example.OpenSky.entities.*;
import com.example.OpenSky.enums.TableType;
import com.example.OpenSky.repositories.BookingRepository;
import com.example.OpenSky.requests.Bill.BillCreateRequest;
import com.example.OpenSky.requests.BillDetail.BillDetailCreateRequest;
import com.example.OpenSky.requests.Booking.BookingCreateRequest;
import com.example.OpenSky.requests.Booking.BookingUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TourService tourService;

    public BookingDto getBookingDto(Booking booking) {
        BookingDto dto = BookingDto.fromEntity(booking);

        Object target = null;
        if (TableType.Hotel.equals(booking.getTableType())) {
            Hotel hotel = hotelService.findById(booking.getTypeId());
            target = hotel != null ? HotelDto.fromEntity(hotel,null) : null;
        } else if (TableType.Tour.equals(booking.getTableType())) {
            Tour tour = tourService.findById(booking.getTypeId());
            target = tour != null ? TourDto.fromEntity(tour,null) : null;
        }
        dto.setTarget(target);
        return dto;
    }

    public Booking findById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found!"));
    }

    public Page<Booking> findByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return  bookingRepository.findByUser_Id(userId,pageable);
    }

    public Page<Booking> findAll( int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return  bookingRepository.findAll(pageable);
    }


    @Transactional
    public Booking create(BookingCreateRequest req,String token) {
        User user = userService.findCurrentUser(token);
        Booking booking = new Booking();
        booking.setCheckInDate(req.getCheckInDate());
        booking.setCheckOutDate(req.getCheckOutDate());
        booking.setUser(user);
        booking.setNotes(req.getNotes());
        booking.setTableType(req.getTableType());
        booking.setTypeId(req.getTypeId());

        Booking savedBooking = bookingRepository.save(booking);

        BillCreateRequest billReq = BillCreateRequest.builder()
                .bookingId(savedBooking.getId())
                .deposit(req.getDeposit())
                .userVoucherId(req.getUserVoucherId())
                .build();

        Bill savedBill = billService.create(billReq);
        for(BillDetailCreateRequest detailReq: req.getBookingDetails()) {
            detailReq.setBillId(savedBill.getId());
            billDetailService.create(detailReq);
        }
        billService.updateTotalPrice(savedBill.getId());
        return savedBooking;
    }

    public Booking update(String id,BookingUpdateRequest req) {
        Booking booking = findById(id);
        if(req.getCheckInDate() != null) {
            booking.setCheckInDate(req.getCheckInDate());
        }
        if(req.getCheckOutDate() != null) {
            booking.setCheckOutDate(req.getCheckOutDate());
        }
        return bookingRepository.save(booking);
    }
}
