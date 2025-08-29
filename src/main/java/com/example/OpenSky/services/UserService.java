package com.example.OpenSky.services;

import com.example.OpenSky.dtos.User.RegisterDto;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.repositories.UserRepository;
import com.example.OpenSky.requests.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UploadService uploadService;

    public Page<User> getAll(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return  userRepository.findAll(pageable);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User findByCitizenId(String citizenId) {
        return userRepository.findByCitizenId(citizenId);
    }

    public RegisterDto register(RegisterRequest req) {
        User user = new User();
        if(findByEmail(req.getEmail()) != null){
            throw new RuntimeException("Email already exists");
        }
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        User savedUser =  userRepository.save(user);
        return new RegisterDto(savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getFullName(),
                savedUser.getCreatedAt());
    }

    public User update(String id, UserUpdateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(req.getFullName() != null){
            user.setFullName(req.getFullName());
        }
        if (req.getPhone() != null) {
            User userWithSamePhone = findByPhone(req.getPhone());

            if (userWithSamePhone != null && !userWithSamePhone.getId().equals(id)) {
                throw new RuntimeException("Phone number already in use!");
            }

            user.setPhone(req.getPhone());
        }
        if(req.getDob() != null) {
            user.setDob(req.getDob());
        }
        if(req.getCitizenId() != null) {
            User userWithSameCitizenId = findByCitizenId(req.getCitizenId());

            if (userWithSameCitizenId != null && !userWithSameCitizenId.getId().equals(id)) {
                throw new RuntimeException("CitizenId number already in use!");
            }

            user.setPhone(req.getPhone());
        }
        if(req.getFile() != null) {
            if(user.getAvatarUrl() != null) {
                uploadService.delete(user.getAvatarUrl());
            }
            String uploadImg = uploadService.upload(req.getFile(), "avatar");
            user.setAvatarUrl(uploadImg);
        }
        return userRepository.save(user);
    }

    public User createUser(UserCreateRequest req) {
        User user = new User();
        if(findByEmail(req.getEmail()) != null){
            throw new RuntimeException("Email already exists");
        }
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.setRole(req.getRole());
        return userRepository.save(user);
    }

    public Page<User> findUsers(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.searchUsers(keyword,pageable);
    }

    public User login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail());
        if(user == null) {
            throw new RuntimeException("User not found!");
        }
        boolean passwordMatch = passwordEncoder.matches(req.getPassword(), user.getPassword());
        if(!passwordMatch){
            throw new RuntimeException("Wrong password!");
        }
        return user;
    }

    public User createWithGoogle(UserGoogleCreateRequest req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());
        user.setAvatarUrl(req.getAvatarUrl());
        user.setProviderId(req.getProviderId());
        return userRepository.save(user);
    }
}
