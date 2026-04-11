package com.iyeong.portfolio.api._common.controller;

import com.iyeong.common.core.response.ApiResponse;
import com.iyeong.portfolio.api._common.dto.MainOverviewDto;
import com.iyeong.portfolio.api._common.service.MainApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio/main")
public class MainController {

    private final MainApiService mainApiService;

    // 통합 조회 (Overview)
    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<MainOverviewDto.OverviewData>> getOverview() {
        
        // 서비스에서 데이터를 가져옵니다.
        MainOverviewDto.OverviewData resData = mainApiService.getOverviewData();
        
        // 공통 ApiResponse 양식에 담아 200 OK와 함께 반환합니다.
        return ResponseEntity.ok(ApiResponse.success("메인페이지 데이터 조회 성공", resData));
    }
}