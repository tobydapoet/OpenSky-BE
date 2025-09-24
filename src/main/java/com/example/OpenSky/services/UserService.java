package com.example.OpenSky.services;

import com.example.OpenSky.dtos.User.RegisterDto;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.UserRole;
import com.example.OpenSky.repositories.UserRepository;
import com.example.OpenSky.requests.User.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private JwtService jwtService;

    public Page<User> getAll(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return  userRepository.findAll(pageable);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findByPhone(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> findByCitizenId(String citizenId) {
        return userRepository.findByCitizenId(citizenId);
    }

    public User register(RegisterRequest req) {
        User user = new User();
        if (findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng!");
        }
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        return userRepository.save(user);
    }

    public User update(String id, UserUpdateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(req.getFullName() != null){
            user.setFullName(req.getFullName());
        }
        if (req.getPhoneNumber() != null) {
            if (findByPhone(req.getPhoneNumber()).isPresent()) {
                throw new RuntimeException("Số điện thoại này đã được sử dụng!");
            }
            user.setPhoneNumber(req.getPhoneNumber());
        }
        if(req.getDob() != null) {
            user.setDob(req.getDob());
        }
        if(req.getRole() != null) {
            user.setRole(req.getRole());
        }
        if(req.getCitizenId() != null) {
            if(findByCitizenId(req.getCitizenId()).isPresent()) {
                throw  new RuntimeException("Mã căn cước công dân này đã được sử dụng!");
            }
            user.setCitizenId(req.getCitizenId());
        }
        if(req.getAvatar() != null) {
            if(user.getAvatarURL() != null) {
                uploadService.delete(user.getAvatarURL());
            }
            String uploadImg = uploadService.upload(req.getAvatar(), "avatar");
            user.setAvatarURL(uploadImg);
        }
        return userRepository.save(user);
    }

    public User findCurrentUser(String token) {
        Claims claims = jwtService.parseAccessToken(token);
        String userId = claims.get("id", String.class);
        return findById(userId);
    }

    public User updateCurrentUser(String token, UserUpdateRequest req) {
        User user = findCurrentUser(token);
        return update(user.getId(),req);
    }

    public Page<User> findByRole(List<UserRole> roles, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt"));
        return userRepository.findByRoleIn(roles, pageable);
    }

    public User createUser(UserCreateRequest req) {
        User user = new User();
        if (findByEmail(req.getEmail()).isPresent()) {
            throw  new RuntimeException("Email này đã được sử dụng!");
        }
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        if(req.getRole() != null) {
            user.setRole(req.getRole());
        }
        if(req.getDob() != null){
            user.setDob(req.getDob());
        }
        if(req.getAvatar() !=  null){

            String avatar = uploadService.upload(req.getAvatar(), "avatar");
            user.setAvatarURL(avatar);
        }
        if(req.getPhoneNumber() != null){
            if(findByPhone(req.getPhoneNumber()) != null){
                throw  new RuntimeException("Số điện thoại này đã được sử dụng!");
            }
            user.setPhoneNumber(req.getPhoneNumber());
        }
        if(req.getCitizenId() != null){
            if(findByCitizenId(req.getCitizenId()) != null){
                throw  new RuntimeException("Số căn cước đã được sử dụng!");
            }
            user.setCitizenId(req.getCitizenId());
        }
        return userRepository.save(user);
    }

    public Page<User> findUsers(String keyword, List<UserRole> roles, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchUsers(keyword, roles, pageable);
    }

    public User login(LoginRequest req) {
        User user = findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));
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
        user.setAvatarURL(req.getAvatarURL());
        user.setProviderId(req.getProviderId());
        return userRepository.save(user);
    }
}
