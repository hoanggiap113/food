package com.food.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path rootLocation = Paths.get("uploads");
    public String store(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File ảnh không được để trống!");
        }
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
        // Tạo tên file ngẫu nhiên (tránh trùng lặp)
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(fileName);
        // Lưu file vào thư mục uploads
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        // Trả về đường dẫn truy cập ảnh (ví dụ: /uploads/abc.jpg)
        return "/uploads/" + fileName;
    }
}
