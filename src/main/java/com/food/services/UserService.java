package com.food.services;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.UserDetailResponse;

import java.util.List;

public interface UserService {

    long  saveUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void deleteUser(long userId);

    List<UserDetailResponse> getAlluser();

    UserDetailResponse getUser(long userId);

    Boolean isEmailExist(String email);

    UserDetailResponse authenticate(AuthenticationRequestDTO request);

    UserDetailResponse saveOrUpdateGoogleUser(String email, String name);
}
