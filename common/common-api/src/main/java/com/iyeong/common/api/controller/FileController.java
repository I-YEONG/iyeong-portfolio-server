package com.iyeong.common.api.controller;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.common.core.response.ApiResponse;
import com.iyeong.common.core.service.SupabaseStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/common/files")
@RequiredArgsConstructor
public class FileController {

    private final SupabaseStorageService supabaseStorageService;

    @GetMapping("/presigned-url")
    public ResponseEntity<ApiResponse<Map<String, String>>> getPresignedUrl( // 다양한 타입을 반환할 수 있게 <?> 사용
                                                                             @RequestParam("folderPath") String folderPath,
                                                                             @RequestParam("fileName") String fileName) {

        // 1. 파일명 또는 폴더 경로가 비어있을 때
        if (fileName == null || fileName.isBlank()) {
            // BaseException에서 400과 에러코드를 저장
            throw new BaseException(400, "파일 이름은 필수입니다.");
        }

        if (folderPath == null || folderPath.isBlank()) {
            throw new BaseException(400, "폴더 경로는 필수입니다.");
        }

        // 2. 폴더 경로가 '/'로 시작할 때 (Supabase 규칙에 맞춰 에러 발생!)
        if (folderPath.startsWith("/")) {
            throw new BaseException(400, "폴더 경로는 '/'로 시작하면 안 됩니다. (예: stacks)");
        }

        // 3. 폴더 경로가 '/'로 끝날 때 (에러 발생!)
        if (folderPath.endsWith("/")) {
            throw new BaseException(400, "폴더 경로는 '/'로 끝나면 안 됩니다. (예: stacks)");
        }

        String url = supabaseStorageService.generatePresignedUrl(folderPath, fileName);
        // ResponseEntity,ok는 스프링 부트 자체 클래스, 200으로 바로 ()안의 내용을 json으로 담아서 리턴함
        return ResponseEntity.ok(ApiResponse.success("성공!", Map.of("presignedUrl", url)));
    }
}