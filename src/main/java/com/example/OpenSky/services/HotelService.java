package com.example.OpenSky.services;

import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.entities.Hotel;
import com.example.OpenSky.entities.Image;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.HotelStatus;
import com.example.OpenSky.enums.TableTypeImage;
import com.example.OpenSky.enums.UserRole;
import com.example.OpenSky.repositories.HotelRepository;
import com.example.OpenSky.requests.Hotel.HotelRequest;
import com.example.OpenSky.requests.Image.ImageRequest;
import com.example.OpenSky.requests.User.UserUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    public Page<HotelDto> getHotels(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotels = hotelRepository.findAll(pageable);

        return hotels.map(hotel -> {
            List<Image> images = imageService.findByTypeAndId(hotel.getId(), TableTypeImage.Hotel);

            List<Image> singleImageList = images.isEmpty()
                    ? Collections.emptyList()
                    : Collections.singletonList(images.get(0));

            return HotelDto.fromEntity(hotel, singleImageList);
        });
    }

    public Hotel findById(String id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel Not Found!"));
    }

    public HotelDto findByIdWithImage(String id) {
        Hotel hotel =  findById(id);
        List<Image> images = imageService.findByTypeAndId(id,TableTypeImage.Hotel);
        return HotelDto.fromEntity(hotel,images);
    }

    public Page<HotelDto> searchHotel(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotels = hotelRepository.searchHotel(keyword, pageable);

        return hotels.map(hotel -> {
            List<Image> images = imageService.findByTypeAndId( hotel.getId(),TableTypeImage.Hotel);

            return HotelDto.fromEntity(hotel, images);
        });
    }

    public Hotel findByEmail(String email) {
        return hotelRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email is already used!"));
    }

    public Hotel updateCurrentHotel(String token, HotelRequest req) {
        User user = userService.findCurrentUser(token);
        return update(user.getId(),req);
    }

    public HotelDto create(HotelRequest req){
        Hotel hotel = new Hotel();
        User user = userService.findById(req.getUserId());
        if (hotelRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Cannot create Hotel: email already exists!");
        }
        hotel.setUser(user);
        hotel.setAddress(req.getAddress());
        hotel.setName(req.getName());
        if(req.getLatitude() != null) {
            hotel.setLatitude(req.getLatitude());
        }
        if(req.getLongitude() != null) {
            hotel.setLongitude(req.getLongitude());
        }
        if(req.getDescription() != null) {
            hotel.setDescription(req.getDescription());
        }
        hotel.setEmail(req.getEmail() == null ? user.getEmail() : req.getEmail());
        hotel.setProvince(req.getProvince());
        Hotel savedHotel = hotelRepository.save(hotel);
        List<Image> images = new ArrayList<>();
        if(req.getFiles() != null) {
            for (MultipartFile file : req.getFiles()) {
                ImageRequest imageReq = ImageRequest.builder()
                    .file(file)
                    .tableType(TableTypeImage.Hotel)
                    .typeId(savedHotel.getId()).build();
                Image savedImg = imageService.create(imageReq);
                images.add(savedImg);
            }
        }
        UserUpdateRequest userReq = UserUpdateRequest.builder()
                .role(UserRole.Hotel)
                .build();
        userService.update(user.getId(),userReq);
        return HotelDto.fromEntity(hotel, images);
    }

    public Hotel updateRole(String id, HotelStatus status) {
        Hotel hotel = findById(id);
        hotel.setStatus(status);
        return hotelRepository.save(hotel);
    }

    @Transactional
    public Hotel update(String id, HotelRequest req){
        Hotel hotel = findById(id);
        if (req.getUserId() != null) {
            User user = userService.findById(req.getUserId());
            hotel.setUser(user);
        }
        if (req.getEmail() != null) {
            hotel.setEmail(req.getEmail());
        }
        if (req.getAddress() != null) {
            hotel.setAddress(req.getAddress());
        }
        if (req.getProvince() != null) {
            hotel.setProvince(req.getProvince());
        }
        if (req.getLongitude() != null) {
            hotel.setLongitude(req.getLongitude());
        }
        if (req.getLatitude() != null) {
            hotel.setLatitude(req.getLatitude());
        }
        if (req.getName() != null) {
            hotel.setName(req.getName());
        }
        if (req.getDescription() != null) {
            hotel.setDescription(req.getDescription());
        }
        List<Image> images = new ArrayList<>();

        Hotel updatedHotel = hotelRepository.save(hotel);
        if (req.getFiles() != null) {
            imageService.deleteByTypeIdAndTableType(updatedHotel.getId(), TableTypeImage.Hotel);

            for (MultipartFile file : req.getFiles()) {
                ImageRequest imageReq = ImageRequest.builder()
                        .file(file)
                        .tableType(TableTypeImage.Hotel)
                        .typeId(updatedHotel.getId())
                        .build();
                Image savedImg = imageService.create(imageReq);
                images.add(savedImg);
            }
        }
        return updatedHotel;
    }
}
