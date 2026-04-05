package com.iyeong.portfolio.api.experience.controller;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.common.core.response.ApiResponse;
import com.iyeong.portfolio.api.experience.dto.ExperienceDto;
import com.iyeong.portfolio.api.experience.service.ExperienceApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON 형태로 응답하는 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/api/experience")
public class ExperienceController {

    private final ExperienceApiService experienceApiService;

    // 1. 자격증 추가 (POST)
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createExperience(@Valid @RequestBody ExperienceDto.Request request) {

        experienceApiService.createExperience(request);
        return ResponseEntity.ok(ApiResponse.success("스택이 성공적으로 등록되었습니다.", null));
    }

    // 2. 자격증 전체 조회 (GET)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceDto.Response>>> getAllExperiences() {
        List<ExperienceDto.Response> experiences = experienceApiService.getAllExperiences();
        return ResponseEntity.ok(ApiResponse.success("스택 목록 조회 성공", experiences));
    }

    // 3. 자격증 수정 (PUT)
    @PutMapping("/{experienceId}")
    public ResponseEntity<ApiResponse<Void>> updateExperience(
            @PathVariable Long experienceId,
            @Valid @RequestBody ExperienceDto.UpdateRequest request) {

        // 파라미터 !== Body.id == 에러
        if (!experienceId.equals(request.getId())) {
            throw new BaseException(400, "경로의 ID와 요청 데이터의 ID가 일치하지 않습니다.");
        }

        experienceApiService.updateExperience(request);
        return ResponseEntity.ok(ApiResponse.success("자격증이 성공적으로 수정되었습니다.", null));
    }

    // 4. 자격증 삭제 (DELETE)
    @DeleteMapping("/{experienceId}")
    public ResponseEntity<ApiResponse<Void>> deleteStack(@Valid @PathVariable Long experienceId) {
        experienceApiService.deleteExperience(experienceId);
        return ResponseEntity.ok(ApiResponse.success("스택이 성공적으로 삭제되었습니다.", null));
    }

}