package com.iyeong.common.api.exception;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.common.core.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ApiResponse.error(e.getMessage()));
    }

    // @Valid, @Pattern 에러처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {

        // DTO의 message 뽑는 구문
        String errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();

        // 400(Bad Request) ApiResponse 포맷
        return ResponseEntity
                .status(400)
                .body(ApiResponse.error(errorMessage));
    }
}