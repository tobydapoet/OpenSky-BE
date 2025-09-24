package com.example.OpenSky.services;

import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.dtos.HotelRoom.HotelRoomDto;
import com.example.OpenSky.entities.Hotel;
import com.example.OpenSky.entities.HotelRoom;
import com.example.OpenSky.entities.Image;
import com.example.OpenSky.enums.HotelRoomStatus;
import com.example.OpenSky.enums.TableTypeImage;
import com.example.OpenSky.repositories.HotelRoomRepository;
import com.example.OpenSky.requests.HotelRoom.HotelRoomCreateRequest;
import com.example.OpenSky.requests.HotelRoom.HotelRoomUpdateRequest;
import com.example.OpenSky.requests.Image.ImageRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelRoomService {
    @Autowired
    HotelRoomRepository hotelRoomRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    HotelService hotelService;

    public Page<HotelRoomDto> findByHotel(String hotelId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<HotelRoom> rooms = hotelRoomRepository.findByHotel_Id(hotelId,pageable);
        return rooms.map(room -> {
            List<Image> images = imageService.findByTypeAndId(hotelId, TableTypeImage.HotelRoom);
            return HotelRoomDto.fromEntity(room,images);
        });
    }

    public HotelRoom findById(String id) {
        return hotelRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel Not Found!"));
    }

    public HotelRoomDto findByIdWithImage(String id) {
        HotelRoom room = findById(id);
        List<Image> images = imageService.findByTypeAndId(id, TableTypeImage.HotelRoom);
        return HotelRoomDto.fromEntity(room, images);
    }

    public void updateStatus(String id, HotelRoomStatus status) {
        HotelRoom hotelRoom = findById(id);
        hotelRoom.setStatus(status);
        hotelRoomRepository.save(hotelRoom);
    }

    public Page<HotelRoomDto> searchRooms(String keyword,String hotelId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HotelRoom> hotelRooms = hotelRoomRepository.findByRoomNameContainingIgnoreCaseAndHotel_Id(keyword,hotelId, pageable);
        return hotelRooms.map(hotelRoom -> {
            List<Image> images = imageService.findByTypeAndId( hotelRoom.getId(),TableTypeImage.HotelRoom);

            return HotelRoomDto.fromEntity(hotelRoom, images);
        });
    }

    public HotelRoomDto create(HotelRoomCreateRequest req) {
        Hotel hotel = hotelService.findById(req.getHotelId());

        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setRoomName(req.getRoomName());
        hotelRoom.setPrice(req.getPrice());
        hotelRoom.setRoomType(req.getRoomType());
        hotelRoom.setHotel(hotel);
        hotelRoom.setMaxPeople(req.getMaxPeople());

        if (req.getAddress() != null) {
            hotelRoom.setAddress(req.getAddress());
        }

        HotelRoom savedHotelRoom = hotelRoomRepository.save(hotelRoom);

        List<Image> images = new ArrayList<>();
        if (req.getFiles() != null) {
            for (MultipartFile file : req.getFiles()) {
                ImageRequest imageRequest = ImageRequest.builder()
                        .file(file)
                        .typeId(savedHotelRoom.getId())
                        .tableType(TableTypeImage.HotelRoom)
                        .build();

                Image savedImg = imageService.create(imageRequest);
                images.add(savedImg);
            }
        }

        return HotelRoomDto.fromEntity(savedHotelRoom, images);
    }

    @Transactional
    public HotelRoomDto update(String id, HotelRoomUpdateRequest req) {
        HotelRoom hotelRoom = findById(id);

        if (req.getRoomName() != null) {
            hotelRoom.setRoomName(req.getRoomName());
        }
        if (req.getPrice() != null) {
            hotelRoom.setPrice(req.getPrice());
        }
        if (req.getRoomType() != null) {
            hotelRoom.setRoomType(req.getRoomType());
        }
        if (req.getMaxPeople() != null) {
            hotelRoom.setMaxPeople(req.getMaxPeople());
        }
        if (req.getAddress() != null) {
            hotelRoom.setAddress(req.getAddress());
        }

        HotelRoom updatedHotelRoom = hotelRoomRepository.save(hotelRoom);

        List<Image> images = imageService.findByTypeAndId(updatedHotelRoom.getId(), TableTypeImage.HotelRoom);

        if (req.getFiles() != null && !req.getFiles().isEmpty()) {
            imageService.deleteByTypeIdAndTableType(updatedHotelRoom.getId(), TableTypeImage.HotelRoom);

            for (MultipartFile file : req.getFiles()) {
                ImageRequest imageRequest = ImageRequest.builder()
                    .file(file)
                    .typeId(updatedHotelRoom.getId())
                    .tableType(TableTypeImage.HotelRoom)
                    .build();
                Image savedImg = imageService.create(imageRequest);
                images.add(savedImg);
            }
        }

        return HotelRoomDto.fromEntity(updatedHotelRoom, images);
    }
}
