package com.food.controllers;

import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.ApiResponse;
import com.food.dto.response.UserDetailResponse;
import com.food.services.IUserService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/adduser")
    public ResponseEntity<ApiResponse<UserDetailResponse>> addUser(@RequestBody UserRequestDTO user) {
        log.info("Request add user: {}", user.getName());

        UserDetailResponse response = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Add user success"));
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
        return userService.getUserById(userId);
    }

    @GetMapping(value = "/")
    public List<UserDetailResponse> getAllUser() {
        log.info("Request get all user");
        return userService.getAlluser();
    }

    @PutMapping(value = "/{userId}")
    public void updateUser(@RequestBody UserRequestDTO body, @PathVariable @Min(value = 1, message = "User id must be greater than 0") Long userId) {
        log.info("Update user with Id = {}", userId);
        userService.updateUser(userId, body);
    }

}
