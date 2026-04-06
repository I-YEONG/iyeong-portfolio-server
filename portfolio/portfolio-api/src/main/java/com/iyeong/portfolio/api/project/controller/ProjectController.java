package com.iyeong.portfolio.api.project.controller;

import com.iyeong.common.core.response.ApiResponse;
import com.iyeong.portfolio.api.project.dto.ProjectDto;
import com.iyeong.portfolio.api.project.dto.ProjectImageDto;
import com.iyeong.portfolio.api.project.dto.ProjectDetailDto;
import com.iyeong.portfolio.api.project.service.ProjectApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectApiService projectApiService;

    // =========================================================================
    // 1. 메인 프로젝트 CRUD
    // =========================================================================

    // 1. 프로젝트 생성 (POST)
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDto.Response>> createProject(@Valid @RequestBody ProjectDto.Request request) {
        ProjectDto.Response resData = projectApiService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("프로젝트가 성공적으로 등록되었습니다.", resData));
    }

    // 2. 프로젝트 전체 조회 (GET) - (포트폴리오 메인 화면용)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectDto.Response>>> getAllProjects() {
        List<ProjectDto.Response> resData = projectApiService.getAllProjects();
        return ResponseEntity.ok(ApiResponse.success("프로젝트 목록 조회 성공", resData));
    }

    // 3. 프로젝트 단건 조회 (GET) - (프로젝트 상세 화면용)
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectDto.Response>> getProject(@PathVariable Long projectId) {
        ProjectDto.Response resData = projectApiService.getProject(projectId);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 단건 조회 성공", resData));
    }

    // 4. 프로젝트 전체 수정 (PUT)
    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectDto.Response>> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectDto.UpdateRequest request) {

        // 🌟 프론트엔드가 URL 파라미터로 보낸 ID를 DTO에 강제로 세팅해 줍니다. (안전장치)
        // (주의: DTO의 id 필드에 @Setter가 있거나, 아래처럼 서비스에 projectId를 따로 넘기도록 수정해야 합니다.)
        ProjectDto.Response resData = projectApiService.updateProject(projectId, request);
        return ResponseEntity.ok(ApiResponse.success("프로젝트가 성공적으로 수정되었습니다.", resData));
    }

    // 5. 프로젝트 삭제 (DELETE)
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long projectId) {
        projectApiService.deleteProject(projectId);
        return ResponseEntity.ok(ApiResponse.success("프로젝트가 삭제되었습니다.", null));
    }


    // =========================================================================
    // 2. 서브 리소스 (개별 이미지 & 디테일 관리)
    // =========================================================================

    // 6. 개별 이미지 추가 (POST)
    @PostMapping("/{projectId}/images")
    public ResponseEntity<ApiResponse<Void>> addImage(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectImageDto.Request request) {
        projectApiService.addImageToProject(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("이미지가 성공적으로 추가되었습니다.", null));
    }

    // 7. 개별 이미지 삭제 (DELETE)
    @DeleteMapping("/{projectId}/images/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(
            @PathVariable Long projectId,
            @PathVariable Long imageId) {
        projectApiService.deleteImageFromProject(projectId, imageId);
        return ResponseEntity.ok(ApiResponse.success("이미지가 삭제되었습니다.", null));
    }

    // 8. 개별 디테일 업데이트 (POST)
    @PostMapping("/{projectId}/details")
    public ResponseEntity<ApiResponse<Void>> updateDetail(
            @PathVariable Long projectId,
            @RequestBody ProjectDetailDto.Request request) {
        projectApiService.updateDetailOfProject(projectId, request);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 상세 정보가 업데이트되었습니다.", null));
    }

    @PutMapping("/{projectId}/details/{detailId}")
    public ResponseEntity<ApiResponse<Void>> modifyDetail(
            @PathVariable Long projectId,
            @PathVariable Long detailId,
            @RequestBody ProjectDetailDto.Request request) {

        projectApiService.modifySpecificDetail(projectId, detailId, request);
        return ResponseEntity.ok(ApiResponse.success("특정 디테일 정보가 수정되었습니다.", null));
    }
}