package com.food.services.impl;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.model.entities.Role;
import com.food.model.entities.User;
import com.food.repositories.RoleRepository;
import com.food.repositories.UserRepository;
import com.food.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public long saveUser(UserRequestDTO request) {
        if (isEmailExist(request.getEmail())) {
            return 0;
        }

        Role role = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Role with ID 1 (USER) not found"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword())) // Mã hóa password
                .role(role)
                .build();

        userRepository.save(user);

        log.info("User has saved!");
        return user.getId();
    }

    @Override
    public void updateUser(long userId, UserRequestDTO request) {
        // Triển khai sau nếu cần
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDetailResponse> getAlluser() {
        return userRepository.findAll().stream()
                .map(user -> UserDetailResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse getUser(long userId) {
        return userRepository.findById(userId)
                .map(user -> UserDetailResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .address(user.getAddress())
                        .build())
                .orElse(null);
    }

    @Override
    public Boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetailResponse authenticate(AuthenticationRequestDTO request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng");
        }

        User user = userOptional.get();

        System.out.println("Stored Password: " + user.getPassword());
        System.out.println("Raw Password: " + request.getPassWord());

        if (!passwordEncoder.matches(request.getPassWord(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng");
        }

        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }


    @Override
    public UserDetailResponse saveOrUpdateGoogleUser(String email, String name) {
        // Kiểm tra xem email đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;
        if (existingUser.isPresent()) {
            // Nếu user đã tồn tại, cập nhật tên nếu cần
            user = existingUser.get();
            if (!user.getName().equals(name)) {
                user.setName(name);
                userRepository.save(user);
                log.info("Updated Google user with email: {}", email);
            }
        } else {
            // Nếu user chưa tồn tại, tạo mới
            Role role = roleRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Role with ID 1 (USER) not found"));

            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode("google-auth-" + UUID.randomUUID())) // Password giả
                    .role(role)
                    .build();

            userRepository.save(user);
            log.info("Saved new Google user with email: {}", email);
        }

        // Trả về thông tin user để tạo JWT
        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }
}