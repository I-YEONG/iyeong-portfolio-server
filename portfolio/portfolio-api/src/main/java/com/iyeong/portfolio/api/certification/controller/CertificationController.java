package com.iyeong.portfolio.api.certification.controller;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.common.core.response.ApiResponse;
import com.iyeong.portfolio.api.certification.dto.CertificationDto;
import com.iyeong.portfolio.api.certification.service.CertificationApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON 형태로 응답하는 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/api/certification")
public class CertificationController {

    private final CertificationApiService certificationApiService;

    // 1. 자격증 추가 (POST)
    @PostMapping
    public ResponseEntity<ApiResponse<CertificationDto.Response>> createStack(@Valid @RequestBody CertificationDto.Request request) {

        CertificationDto.Response resData = certificationApiService.createStack(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("자격증이 성공적으로 등록되었습니다.", resData));
    }

    // 2. 자격증 전체 조회 (GET)
    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificationDto.Response>>> getAllCertifications() {
        List<CertificationDto.Response> stacks = certificationApiService.getAllCertifications();
        return ResponseEntity.ok(ApiResponse.success("자격증이 목록 조회 성공", stacks));
    }

    // 3. 자격증 수정 (PUT)
    @PutMapping("/{certificationId}")
    public ResponseEntity<ApiResponse<Void>> updateCertification(
            @PathVariable Long certificationId,
            @Valid @RequestBody CertificationDto.UpdateRequest request) {

        // 파라미터 !== Body.id == 에러
        if (!certificationId.equals(request.getId())) {
            throw new BaseException(400, "경로의 ID와 요청 데이터의 ID가 일치하지 않습니다.");
        }

        certificationApiService.updateCertification(request);
        return ResponseEntity.ok(ApiResponse.success("자격증이 성공적으로 수정되었습니다.", null));
    }

    // 4. 자격증 삭제 (DELETE)
    @DeleteMapping("/{certificationId}")
    public ResponseEntity<ApiResponse<Void>> deleteStack(@Valid @PathVariable Long stackId) {
        certificationApiService.deleteStack(stackId);
        return ResponseEntity.ok(ApiResponse.success("자격증이 성공적으로 삭제되었습니다.", null));
    }

}