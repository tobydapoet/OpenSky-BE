package com.example.OpenSky.controllers;

import com.example.OpenSky.dtos.User.RegisterDto;
import com.example.OpenSky.entities.User;
import com.example.OpenSky.requests.User.RegisterRequest;
import com.example.OpenSky.requests.User.UserCreateRequest;
import com.example.OpenSky.requests.User.UserUpdateRequest;
import com.example.OpenSky.services.UploadService;
import com.example.OpenSky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UploadService uploadService;

    @GetMapping
    public Page<User> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping("/search")
    public Page<User> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return userService.findUsers(keyword, page, size);
    }

    @PostMapping("/register")
    public RegisterDto register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PostMapping()
    public User create(@RequestBody UserCreateRequest req) {
        return userService.createUser(req);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable String id, @RequestBody UserUpdateRequest req) {
        return userService.update(id, req);
    }
}
