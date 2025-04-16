package com.food.exception;

import com.food.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleResponseStatusException(ResponseStatusException ex) {
        String message = ex.getReason();
        HttpStatus status = (HttpStatus) ex.getStatusCode();

        ErrorCode matchedCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        for (ErrorCode code : ErrorCode.values()) {
            if (code.getMessage().equalsIgnoreCase(message)) {
                matchedCode = code;
                break;
            }
        }

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(matchedCode.getCode(), matchedCode.getMessage(), status.value()));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorCode errorCode = errors.containsKey("email") && errors.get("email").contains("email format")
                ? ErrorCode.INVALID_EMAIL
                : ErrorCode.UNCATEGORIZED_EXCEPTION;

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.error(errorCode.getCode(), errors.values().iterator().next(), errorCode.getStatusCode().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleGenericException(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage(), errorCode.getStatusCode().value()));
    }
}