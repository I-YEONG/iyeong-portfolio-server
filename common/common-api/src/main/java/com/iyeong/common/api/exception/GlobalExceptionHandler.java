package com.iyeong.common.api.exception;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.common.core.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {

        ApiResponse<Void> response = ApiResponse.error(e.getStatus(), e.getMessage());

        return ResponseEntity
                .status(e.getStatus())
                .body(response);
    }
}