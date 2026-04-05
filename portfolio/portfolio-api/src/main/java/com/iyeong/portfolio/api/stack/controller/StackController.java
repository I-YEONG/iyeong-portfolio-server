package com.iyeong.portfolio.api.stack.controller;

import com.iyeong.portfolio.api.stack.service.StackApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iyeong.portfolio.api.stack.dto.StackDto;

import java.util.List;
import com.iyeong.common.core.response.ApiResponse;

@RestController // JSON 형태로 응답하는 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/api/stack")
public class StackController {

    private final StackApiService stackApiService;

    // 1. 스택 추가 (POST)
    @PostMapping
    public ResponseEntity<ApiResponse<StackDto.Response>> createStack(@Valid @RequestBody StackDto.Request request) {

        StackDto.Response resData = stackApiService.createStack(request);
//        return ResponseEntity.ok(ApiResponse.success("스택이 성공적으로 등록되었습니다.", null));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("스택이 성공적으로 등록되었습니다.", resData));
    }

    // 2. 스택 전체 조회 (GET)
    @GetMapping
    public ResponseEntity<ApiResponse<List<StackDto.Response>>> getAllStacks() {
        List<StackDto.Response> stacks = stackApiService.getAllStacks();
        return ResponseEntity.ok(ApiResponse.success("스택 목록 조회 성공", stacks));
    }

    // 3. 스택 삭제 (DELETE)
    @DeleteMapping("/{stackId}")
    public ResponseEntity<ApiResponse<Void>> deleteStack(@Valid @PathVariable Long stackId) {
        stackApiService.deleteStack(stackId);
        return ResponseEntity.ok(ApiResponse.success("스택이 성공적으로 삭제되었습니다.", null));
    }
}