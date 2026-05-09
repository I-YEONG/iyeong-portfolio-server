package com.iyeong.common.core.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final int status;

    public BaseException(int status, String message) {
        super(message); // 부모인 RuntimeException에 메시지 전달
        this.status = status;
    }
}