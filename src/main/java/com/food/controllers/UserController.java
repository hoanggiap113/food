package com.food.controllers;

import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.services.UserService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/adduser")
    public Long addUser(@RequestBody UserRequestDTO user) {
        log.info("Request add user, {}", user.getName());

        long userId = userService.saveUser(user);

        return userId;
    }

    @DeleteMapping(value = "/{userId}")
    public String deleteUser(@PathVariable @Min(value = 1, message = "UserID must be greater than 0") long userId) {
        log.info("Request delete userId={}", userId);
        userService.deleteUser(userId);
        return "User has deleted" + userId;
    }

    @GetMapping(value = "/{userId}")
    public UserDetailResponse getUser(@PathVariable @Min(value = 1, message = "Get user by Id") long userId) {
        log.info("Request get User by Id = {}", userId);
        return userService.getUser(userId);
    }

    @GetMapping(value = "/")
    public List<UserDetailResponse> getAllUser() {
        log.info("Request get all user");
        return userService.getAlluser();
    }

    @PutMapping(value = "/{userId}")
    public void updateUser(@PathVariable @Min(value = 1, message = "User id must be greater than 0") Long userId) {
        log.info("Update user with Id = {}", userId);

    }

}
