package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.User.RegisterDto;
import com.example.OpenSky.dtos.User.UserDto;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.enums.UserRole;
import com.example.OpenSky.requests.User.RegisterRequest;
import com.example.OpenSky.requests.User.UserCreateRequest;
import com.example.OpenSky.requests.User.UserUpdateRequest;
import com.example.OpenSky.services.UploadService;
import com.example.OpenSky.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UploadService uploadService;

    @GetMapping
    public Page<UserDto> findByRole(
            @RequestParam List<UserRole> roles,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ){
        Page<User> userList = userService.findByRole(roles, page, size);
        return userList.map(UserDto::fromEntity);
    }

    @Operation(summary = "Lấy ra thông tin người dùng hiện tại")
    @GetMapping("/profile")
    public UserDto getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        User user = userService.findCurrentUser(token);
        return UserDto.fromEntity(user);
    }

    @Operation(summary = "Tìm kiếm người dùng dựa trên roles",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','TOURGUIDE')")
    @GetMapping("/search")
    public Page<UserDto> searchUsers(
            @RequestParam String keyword,
            @RequestParam List<UserRole> roles,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<User> userList = userService.findUsers(keyword,roles ,page, size);
        return userList.map(UserDto::fromEntity);
    }

    @Operation(summary = "Lấy ra thông tin người dùng theo id",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable String id) {
        User user= userService.findById(id);
        return UserDto.fromEntity(user);
    }

    @Operation(summary = "Thêm người dùng người dùng",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> create(@ModelAttribute UserCreateRequest req) {
        User savedUser = userService.createUser(req);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Thêm người dùng thành công!");
        response.put("profile", UserDto.fromEntity(savedUser));
        return response;
    }

    @Operation(summary = "Cập nhật thông tin người dùng hiện tại",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> updateCurrentUser(HttpServletRequest request,
                                                 @ModelAttribute UserUpdateRequest req) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        User user = userService.updateCurrentUser(token, req);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cập nhật thông tin thành công!");
        response.put("profile", UserDto.fromEntity(user));
        return response;
    }

    @Operation(summary = "Cập nhật thông tin người dùng theo id")
    @PutMapping(value= "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> update(@PathVariable String id, @ModelAttribute UserUpdateRequest req) {
        User updatedUser =  userService.update(id, req);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cập nhật thông tin thành công!");
        response.put("profile", UserDto.fromEntity(updatedUser));
        return response;
    }
}
