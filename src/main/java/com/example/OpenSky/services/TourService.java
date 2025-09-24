package com.example.OpenSky.services;

import com.example.OpenSky.dtos.Hotel.HotelDto;
import com.example.OpenSky.dtos.HotelRoom.HotelRoomDto;
import com.example.OpenSky.dtos.Tour.TourDto;
import com.example.OpenSky.entities.Image;
import com.example.OpenSky.entities.Tour;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.TableTypeImage;
import com.example.OpenSky.repositories.TourRepository;
import com.example.OpenSky.requests.Image.ImageRequest;
import com.example.OpenSky.requests.Tour.TourCreateRequest;
import com.example.OpenSky.requests.Tour.TourUpdateRequest;
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
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    public Page<TourDto> getTours(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tour> tours =  tourRepository.findAll(pageable);
        return tours.map(tour -> {
            List<Image> images = imageService.findByTypeAndId(tour.getId(), TableTypeImage.Tour);
            List<Image> singleImageList = images.isEmpty()
                    ? Collections.emptyList()
                    : Collections.singletonList(images.get(0));
            return TourDto.fromEntity(tour, singleImageList);
        });
    }

    public Tour findById(String id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour Not Found!"));
    }

    public TourDto getTourWithImage(String id) {
        Tour tour = findById(id);
        List<Image> images= imageService.findByTypeAndId(tour.getId(), TableTypeImage.Tour);
        return TourDto.fromEntity(tour, images);
    }

    public Page<TourDto> searchTour(String keyword,int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tour> tours = tourRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return tours.map(tour -> {
            List<Image> images = imageService.findByTypeAndId(tour.getId(), TableTypeImage.Tour);
            return TourDto.fromEntity(tour, images);
        });
    }

    public TourDto create(TourCreateRequest req, String token) {
        Tour tour = new Tour();
        User user = userService.findCurrentUser(token);
        tour.setUser(user);
        tour.setDescription(req.getDescription());
        tour.setName(req.getName());
        tour.setNumberOfDays(req.getNumberOfDays());
        tour.setMaxPeople(req.getMaxPeople());
        Tour savedTour =  tourRepository.save(tour);
        List<Image> images = new ArrayList<>();
        if(req.getFiles() != null) {
            for (MultipartFile file : req.getFiles()) {
                ImageRequest imageReq = ImageRequest.builder()
                        .file(file)
                        .tableType(TableTypeImage.Tour)
                        .typeId(savedTour.getId())
                        .build();
                Image savedImage = imageService.create(imageReq);
                images.add(savedImage);
            }
        }
        return TourDto.fromEntity(tour, images);
    }

    @Transactional
    public TourDto update(String id, TourUpdateRequest req) {
        Tour tour = findById(id);

        if(req.getName() != null) {
            tour.setName(req.getName());
        }
        if(req.getNumberOfDays() != null) {
            tour.setNumberOfDays(req.getNumberOfDays());
        }
        if(req.getMaxPeople() != null) {
            tour.setMaxPeople(req.getMaxPeople());
        }
        if(req.getDescription() != null) {
            tour.setDescription(req.getDescription());
        }
        Tour updatedTour =  tourRepository.save(tour);
        List<Image> images = new ArrayList<>();
        if(req.getFiles() != null) {
            imageService.deleteByTypeIdAndTableType(updatedTour.getId(), TableTypeImage.Tour);
            for (MultipartFile file : req.getFiles()) {
                ImageRequest imageReq = ImageRequest.builder()
                        .file(file)
                        .tableType(TableTypeImage.Tour)
                        .typeId(updatedTour.getId())
                        .build();
                Image savedImage = imageService.create(imageReq);
                images.add(savedImage);
            }
        }
        return TourDto.fromEntity(updatedTour, images);
    }
}
