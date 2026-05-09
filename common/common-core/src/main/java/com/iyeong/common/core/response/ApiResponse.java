package com.iyeong.common.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    // 1. 기존: 데이터만 넣으면 기본 메시지 출력
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "요청에 성공하였습니다.", data);
    }

    // 2. 추가: 컨트롤러에서 "메시지"까지 직접 정하고 싶을 때 사용
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}