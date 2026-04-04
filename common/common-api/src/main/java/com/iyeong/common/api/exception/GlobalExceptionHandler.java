package com.iyeong.common.api.exception;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.common.core.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    // BaseException의 저장값이 있는 e를 불러와서 getMessage, getStatus를 통해 에러로 반환
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {

        return ResponseEntity
                .status(e.getStatus())
                .body(ApiResponse.error(e.getMessage()));
    }
}