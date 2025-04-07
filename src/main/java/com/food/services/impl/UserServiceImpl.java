package com.food.services.impl;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.model.entities.Role;
import com.food.model.entities.User;
import com.food.repositories.RoleRepository;
import com.food.repositories.UserRepository;
import com.food.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public long saveUser(UserRequestDTO request) {
        if (isEmailExist(request.getEmail())) {return 0;}

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

        log.info("User has save!");

        return user.getId();
    }

    @Override
    public void updateUser(long userId, UserRequestDTO request) {
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
        // Kiểm tra email có tồn tại không
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        System.out.println("Stored Password: " + user.get().getPassword());
        System.out.println("Raw Password: " + request.getPassWord());

        // Nếu user không tồn tại hoặc sai mật khẩu, ném lỗi
        if (user.isEmpty() || !passwordEncoder.matches(request.getPassWord(), user.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng");
        }

        return UserDetailResponse.builder()
                .name(user.get().getName())
                .email(user.get().getEmail())
                .build();
    }
}
