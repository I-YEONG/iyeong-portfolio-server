package com.iyeong.portfolio.api.controller;

import com.iyeong.common.core.response.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public ApiResponse<String> test() {
        return ApiResponse.success("멀티 모듈 구조 세팅 완료! 영규님 축하드려요 🚀");
    }
}