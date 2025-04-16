package com.food.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiResponse<T> {
    private String status;
    private T data;
    private Map<String, Object> error;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus("success");
        response.setData(data);
        return response;
    }

    public static ApiResponse<Map<String, Object>> error(int code, String message, int statusCode) {
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setStatus("error");
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("code", code);
        errorDetails.put("message", message);
        errorDetails.put("status", statusCode);
        response.setError(errorDetails);
        return response;
    }
}